package ir.bki.otpservice.controller;

import ir.bki.otpservice.client.NotificationServiceFeign;
import ir.bki.otpservice.model.NotificationRequestDto;
import ir.bki.otpservice.model.ResponseDto;
import ir.bki.otpservice.service.StrongAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
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
@RequestMapping("/v1/otp")
@Slf4j
//https://developer.redis.com/develop/java/redis-and-spring-course/lesson_2/
public class RedisOtpTokenController {

    private final StrongAuthService strongAuthService;
    private final NotificationServiceFeign notificationServiceFeign;

    private static final String SEPARATOR = ":";

    public RedisOtpTokenController(StrongAuthService strongAuthService, NotificationServiceFeign notificationServiceFeign) {
        this.strongAuthService = strongAuthService;
        this.notificationServiceFeign = notificationServiceFeign;
    }

    @PostMapping("/mobiles/{mobile-no}/generation")
    public ResponseEntity<ResponseDto<String>> generateAndSend(
            @PathVariable("mobile-no") String mobileNo ////HC:989176323629:XtLOANJCKa
            , @RequestHeader("Pair-Data") String pairData //4586209;5522;832784307;894010930;175000000
            , @DefaultValue("120") @RequestHeader("Timeout") Long timeout //120 in seconds
            , @DefaultValue("5") @RequestHeader("Otp-Length") Integer otpLength //5
            , @RequestHeader(value = "Authorization") String authorization
            , @RequestBody(required = false) NotificationRequestDto messageBodyRequest
            , HttpServletRequest request) {
        List<String> payload = new ArrayList<>();
        long start = System.currentTimeMillis();
        ResponseDto<String> responseDto = new ResponseDto<>(payload);
        responseDto.setPath(request.getMethod() + " " + request.getRequestURI());
        String code = strongAuthService.generateCode(otpLength);
        String messageForSend = "کد فعالسازی شما: " + "\n" + code;
        if (messageBodyRequest != null && !"".equals(messageBodyRequest.getMessage()))
            messageForSend = messageBodyRequest.getMessage().replace("${code}", code);
        String randomString = strongAuthService.generateStringRandom(10);
        String clientHashKey = "HC" + SEPARATOR + mobileNo + SEPARATOR + randomString; // This will send to client
        String cacheKey = clientHashKey + SEPARATOR + code;
        strongAuthService.put(cacheKey, pairData);
        strongAuthService.expire(cacheKey, Duration.ofSeconds(timeout));
        payload.add(clientHashKey);//Will return to client: HC:989176323629:XtLOANJCKa ; Client must send it in next request
        NotificationRequestDto notificationRequestDto = new NotificationRequestDto(messageForSend);
//        System.out.println("#notificationRequestDto: " + notificationRequestDto);
        ResponseDto<String> responseDtoSms = notificationServiceFeign.send(mobileNo, notificationRequestDto);
        log.info("PUT->cacheKey:" + cacheKey + " ;value: " + pairData);
        log.info("#responseDtoSms = " + responseDtoSms);
        if (responseDtoSms != null && responseDtoSms.getStatus() == 0)
            payload.add(responseDtoSms.getPayload().get(0));
        responseDto.setElapsedTime(System.currentTimeMillis() - start);
        return ResponseEntity.ok(responseDto);//HC:989176323629:XtLOANJCKa
    }

    @PostMapping("/mobiles/{mobile-no}/verification")
    public ResponseEntity<ResponseDto<String>> verify(
            @PathVariable("mobile-no") String mobileNo     //989176323629
            , @RequestHeader("Hash-Key") String clientHashKey //HC:989176323629:XtLOANJCKa
            , @RequestHeader("Pair-Data") String pairDataRequest //Value in cache-> 4586209;5522;832784307;894010930;175000000
            , @RequestHeader("Code") String code  //12345
            , @RequestHeader("Authorization") String authorization
            , HttpServletRequest request) {
        log.info("##mobileNo = " + mobileNo + ", clientHashKey = " + clientHashKey + ", pairData = " + pairDataRequest + ", code = " + code + ", request = " + request);
        List<String> payload = new ArrayList<>();
        long start = System.currentTimeMillis();
        ResponseDto<String> responseDto = new ResponseDto<>(payload);
        responseDto.setPath(request.getMethod() + " " + request.getRequestURI());
        responseDto.setStatus(40401);
        responseDto.setHttpStatus(404);
        responseDto.setMessage("کلید در کش پیدا نشد");
        String cacheKey = clientHashKey + SEPARATOR + code;//HC:989176323629:XtLOANJCKa:12345
        String cacheValue = strongAuthService.get(cacheKey); //HC:989176323629:XtLOANJCKa:12345
        log.info("cacheKey: " + cacheKey + " ;cacheValue: " + cacheValue);
        if (cacheValue != null) {
            // pairData found in cache
            if (pairDataRequest != null && !"".equals(pairDataRequest) && cacheValue.equals(pairDataRequest)) {
                responseDto.setStatus(0);
                responseDto.setMessage("موفق");
                strongAuthService.del(cacheKey);
            } else {
                responseDto.setStatus(40402);
                responseDto.setMessage("مقدار با کلید مطابقت ندارد");
            }
        }
        responseDto.setElapsedTime(System.currentTimeMillis() - start);
        return ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);
    }

    @GetMapping("{key}") // TODO Remove on Pro
    public ResponseEntity<ResponseDto<String>> get(@PathVariable("key") String key, HttpServletRequest request) {
        List<String> payload = new ArrayList<>();
        ResponseDto<String> responseDto = new ResponseDto<>(payload);
        responseDto.setPath(request.getMethod() + " " + request.getRequestURI());
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
