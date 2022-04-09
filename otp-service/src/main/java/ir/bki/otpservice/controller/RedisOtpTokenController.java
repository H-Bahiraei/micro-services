package ir.bki.otpservice.controller;

import com.google.inject.internal.asm.$TypePath;
import ir.bki.otpservice.client.NotificationServiceFeign;
import ir.bki.otpservice.model.NotificationRequestDto;
import ir.bki.otpservice.model.ResponseDto;
import ir.bki.otpservice.service.StrongAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HeaderParam;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/5/2022
 */
@RestController
@RequestMapping("sotp")
//https://developer.redis.com/develop/java/redis-and-spring-course/lesson_2/
public class RedisOtpTokenController {

    StrongAuthService strongAuthService;
    NotificationServiceFeign notificationServiceFeign;

    public RedisOtpTokenController(StrongAuthService strongAuthService, NotificationServiceFeign notificationServiceFeign) {
        this.strongAuthService = strongAuthService;
        this.notificationServiceFeign = notificationServiceFeign;
    }

    @PostMapping("/keys/{key}/generation")
    public ResponseEntity<ResponseDto<String>> generateAndSend(@PathVariable("key") String key, HttpServletRequest request) {
        List<String> payload = new ArrayList<>();
        long start=System.currentTimeMillis();
        ResponseDto<String> responseDto = new ResponseDto<>(payload);
        responseDto.setPath(request.getMethod()+" "+request.getRequestURI());
        String code = strongAuthService.generateCode();
        strongAuthService.put(key, code);
        strongAuthService.expire(key, Duration.ofSeconds(5*60));
        payload.add(code);
        NotificationRequestDto notificationRequestDto=new NotificationRequestDto("کد فعالسازی شما: " + "\n"+code);
        System.out.println("#notificationRequestDto: "+notificationRequestDto);
        notificationServiceFeign.send(key,notificationRequestDto);
        responseDto.setElapsedTime(System.currentTimeMillis()-start);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/keys/{key}/verification")
    public ResponseEntity<ResponseDto<String>> verify(@PathVariable("key") String key,@RequestHeader("Code") String code) {
        System.err.println("###key = " + key + ", code = " + code);
        List<String> payload = new ArrayList<>();
        long start=System.currentTimeMillis();
        ResponseDto<String> responseDto = new ResponseDto<>(payload);
        responseDto.setStatusCode(40401);
        responseDto.setHttpStatus(404);
        responseDto.setMessage("پیدا نشد داداش");
        String codeInCache = strongAuthService.get(key);
        if (code!=null && !"".equals(code) && code.equals(codeInCache)) {
            responseDto.setStatusCode(0);
            responseDto.setMessage("موفق");
            strongAuthService.del(key);
        }
        responseDto.setElapsedTime(System.currentTimeMillis()-start);
        return ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);
    }

    @GetMapping("{key}") // TODO Remove on Pro
    public ResponseEntity<ResponseDto<String>> get(@PathVariable("key") String key , HttpServletRequest request) {
        List<String> payload = new ArrayList<>();
        ResponseDto<String> responseDto = new ResponseDto<>(payload);
        responseDto.setPath(request.getMethod()+" "+request.getRequestURI());
        payload.add(strongAuthService.get(key));
        return ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);
    }

    @GetMapping("s/{id}")
    public ResponseEntity<String> sget(@PathVariable("id") String id) {
        return ResponseEntity.ok(strongAuthService.get("2", id));
    }

    @GetMapping("s/put/h")
    public ResponseEntity<String> sputh() {
        System.out.println("####PUT");
        strongAuthService.put("2", "f", "v");
        return ResponseEntity.ok("ADDED");
    }

}
