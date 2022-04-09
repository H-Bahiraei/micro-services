package ir.bki.otpservice.controller;

import com.google.inject.internal.asm.$TypePath;
import ir.bki.otpservice.client.NotificationServiceFeign;
import ir.bki.otpservice.model.NotificationRequestDto;
import ir.bki.otpservice.model.ResponseDto;
import ir.bki.otpservice.service.StrongAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("redis2")
//https://developer.redis.com/develop/java/redis-and-spring-course/lesson_2/
public class RedisOtpTokenController {

    StrongAuthService strongAuthService;
    NotificationServiceFeign notificationServiceFeign;

    public RedisOtpTokenController(StrongAuthService strongAuthService, NotificationServiceFeign notificationServiceFeign) {
        this.strongAuthService = strongAuthService;
        this.notificationServiceFeign = notificationServiceFeign;
    }

    @GetMapping("{key}/create")
    public ResponseEntity<ResponseDto<String>> generateAndSend(@PathVariable("key") String key, HttpServletRequest request) {
        List<String> payload = new ArrayList<>();
        ResponseDto<String> responseDto = new ResponseDto<>(payload);
        responseDto.setPath(request.getMethod()+" "+request.getRequestURI());
        String code = strongAuthService.generateCode();
        strongAuthService.put(key, code);
        strongAuthService.expire(key, Duration.ofSeconds(30));
        System.out.println("KEY: " + key + "->" + code);
        payload.add(code);
        NotificationRequestDto notificationRequestDto=new NotificationRequestDto("کد فعالسازی شما: " + "\n"+code);
        notificationServiceFeign.send(key,notificationRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("{key}")
    public ResponseEntity<ResponseDto<String>> get(@PathVariable("key") String key , HttpServletRequest request) {
        List<String> payload = new ArrayList<>();
        ResponseDto<String> responseDto = new ResponseDto<>(payload);
        responseDto.setPath(request.getMethod()+" "+request.getRequestURI());
        payload.add(strongAuthService.get(key));
        return ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);
    }

    @GetMapping("{key}/verify")
    public ResponseEntity<ResponseDto<String>> verify(@PathVariable("key") String key, @HeaderParam("Code") String code) {
        List<String> payload = new ArrayList<>();
        ResponseDto<String> responseDto = new ResponseDto<>(payload);
        String codeInCache = strongAuthService.get(key);
        if (code.equals(codeInCache)) {
            responseDto.setStatusCode(40001);
            responseDto.setStatusCode(400);
        }
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
