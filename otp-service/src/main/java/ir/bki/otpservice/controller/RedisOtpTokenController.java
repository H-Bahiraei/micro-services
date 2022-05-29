package ir.bki.otpservice.controller;

import ir.bki.otpservice.apects.Loggable;
import ir.bki.otpservice.client.NotificationServiceFeign;
import ir.bki.otpservice.exception.BadRequestAlertException;
import ir.bki.otpservice.model.NotificationRequestDto;
import ir.bki.otpservice.model.ResponseDto;
import ir.bki.otpservice.service.ResponseDtoServiceImpl;
import ir.bki.otpservice.service.StrongAuthService;
import ir.bki.otpservice.service.impl.ResponseDtoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static ir.bki.otpservice.exception.ErrorCodeConstants.*;

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

    private static final String SEPARATOR = ":";
    private final StrongAuthService strongAuthService;  // ??? final
    private final NotificationServiceFeign notificationServiceFeign;

    @Autowired
    private  ResponseDtoService responseDtoService ;

    public RedisOtpTokenController(StrongAuthService strongAuthService, NotificationServiceFeign notificationServiceFeign) {
        this.strongAuthService = strongAuthService;
        this.notificationServiceFeign = notificationServiceFeign;
    }

    @Loggable
    @PostMapping("/mobiles/{mobile-no}/generation")
    public ResponseEntity<ResponseDto<String>> generateAndSend(
            @PathVariable("mobile-no") String mobileNo ////HC:989176323629:XtLOANJCKa
            , @RequestHeader("Pair-Data") String pairData //4586209;5522;832784307;894010930;175000000
            , @DefaultValue("120") @RequestHeader("Timeout") Long timeout //120 in seconds
            , @DefaultValue("5") @RequestHeader("Otp-Length") Integer otpLength //5
            , @RequestHeader(value = "Authorization") String authorization
            , @RequestBody(required = false) NotificationRequestDto messageBodyRequest
            , HttpServletRequest request) throws BadRequestAlertException {

        //            System.out.println("mobileNo = " + mobileNo + ", pairData = " + pairData + ", timeout = " + timeout + ", otpLength = " + otpLength + ", authorization = " + authorization + ", messageBodyRequest = " + messageBodyRequest + ", request = " + request);

        List<String> payload = new ArrayList<>();
        long start = System.currentTimeMillis();

        mobileNo= strongAuthService.correctMobileNo(mobileNo);
        if (!mobileNo.substring(0,2).equals("98"))
            throw new BadRequestAlertException(request.getMethod() + "-->" + request.getRequestURI(),
                    INVALID_MOBILE_NO, mobileNo, start);


        ResponseDto<String> responseDto = new ResponseDto<>(payload);
//        responseDto.setPath(request.getMethod() + " " + request.getRequestURI());
        responseDto.setPath(request.getMethod() + " "
                + request.getServletPath()  + " "
                + "Pair-Data:" +request.getHeader("Pair-Data") + " "
                + "Authorization:" + request.getHeader("Authorization") + " "
                + "Otp-Length"+ request.getHeader("Otp-Length") + " "
                + "Timeout:" + request.getHeader("Timeout") + " "
        );

        boolean isMobileBlocked = strongAuthService.isMobileBlock(mobileNo);
//        strongAuthService.deleteFailedAttemptByMobileNo(mobile No)
        if (!isMobileBlocked) {
            String code = strongAuthService.generateCode(otpLength);
            String messageForSend = "کد فعالسازی شما: " + "\n" + code;
            if (messageBodyRequest != null && !"".equals(messageBodyRequest.getMessage()))
                messageForSend = messageBodyRequest.getMessage().replace("${code}", code);
            String randomString = strongAuthService.generateStringRandom(10);
            String clientHashKey = "HC" + SEPARATOR + mobileNo + SEPARATOR + randomString; // This will send to client
            String cacheKey = clientHashKey + SEPARATOR + code;
            strongAuthService.put(cacheKey, pairData); // cache key: HC:09216017504:TylIvJcoQa:66688 pairData: 20000;transfer;application;ib;tr12345
            strongAuthService.expire(cacheKey, Duration.ofSeconds(timeout));
            payload.add(clientHashKey);//Will return to client: HC:989176323629:XtLOANJCKa ; Client must send it in verify request
            NotificationRequestDto notificationRequestDto = new NotificationRequestDto(messageForSend);
            ResponseDto<String> responseDtoSms = notificationServiceFeign.send(mobileNo, notificationRequestDto);// ???
//            log.info("PUT->cacheKey:" + cacheKey + " ;  value: " + pairData);
//            log.info("#responseDtoSms = " + responseDtoSms);
            if (responseDtoSms != null )
                payload.add(responseDtoSms.getPayload().get(0));
            responseDto.setHttpStatus(HttpStatus.CREATED.value());
            responseDto.setMessage("code is sended");
            responseDto.setElapsedTime(System.currentTimeMillis() - start);
            responseDtoService.createRdtoIndex(responseDto);
            return ResponseEntity
                    .status(responseDto.getHttpStatus()).
                    body(responseDto);
        } else {
            throw new BadRequestAlertException(request.getMethod() + "-->" + request.getRequestURI(),
                    USER_IS_Blocked, "user is blocked", start);
        }
    }

    @Loggable
    @PostMapping("/mobiles/{mobile-no}/verification")
    public ResponseEntity<ResponseDto<String>> verify(
            @PathVariable("mobile-no") String mobileNo     //989176323629
            , @RequestHeader("Hash-Key") String clientHashKey // HC:989176323629:XtLOANJCKa -> Redis Key:
            , @RequestHeader("Pair-Data") String pairDataRequest //Value(pd) in cache-> 4586209;5522;832784307;894010930;175000000
            , @RequestHeader("Code") String code  //12345
            , @RequestHeader("Authorization") String authorization
            , HttpServletRequest request) throws Exception {
//        log.info("##mobileNo = " + mobileNo + ", clientHashKey = " + clientHashKey + ", pairData = " + pairDataRequest + ", code = " + code + ", request = " + request);
        List<String> payload = new ArrayList<>();
        long start = System.currentTimeMillis();
        ResponseDto<String> responseDto = new ResponseDto<>(payload);

        responseDto.setPath(request.getMethod() + " "
                + request.getServletPath()  + " "
                + request.getHeader("Hash-Key")  + " "
                + request.getHeader("Code") + " "
                + request.getHeader("Authorization")  + " "
                + request.getHeader("Pair-Data")  + " "
        );
        mobileNo= strongAuthService.correctMobileNo(mobileNo);
        if (!mobileNo.substring(0,2).equals("98"))
            throw new BadRequestAlertException(request.getMethod() + "-->" + request.getRequestURI(),
                    INVALID_MOBILE_NO, mobileNo, start);

        boolean isblocked = strongAuthService.isMobileBlock(mobileNo);
        if (isblocked) {
            throw new BadRequestAlertException(request.getMethod() + "-->" + request.getRequestURI(),
                    USER_IS_Blocked, "user is blocked", start);

        } else {
            String cacheKey = clientHashKey + SEPARATOR + code;//HC:989176323629:XtLOANJCKa:12345
            String cacheValue = strongAuthService.get(cacheKey); //HC:989176323629:XtLOANJCKa:12345
//            log.info("cacheKey: " + cacheKey + " ;cacheValue: " + cacheValue);
            if (cacheValue != null) {
                // pairData found in cache
                if (pairDataRequest != null && !"".equals(pairDataRequest)
                        && cacheValue.equals(pairDataRequest)) {
                    responseDto.setHttpStatus(HttpStatus.OK.value());
                    responseDto.setMessage("ok");
                    strongAuthService.del(cacheKey);
                    strongAuthService.deleteFailedAttemptByMobileNo(mobileNo);
                } else {
                    strongAuthService.createFailedAttempt(mobileNo);
                    throw new BadRequestAlertException(request.getMethod() + "-->" + request.getRequestURI(),
                            Value_mismatch_with_Key, "Value mismatch with Key", start);
                }
            }
            else {
                strongAuthService.createFailedAttempt(mobileNo);
                throw new BadRequestAlertException(request.getMethod() + "-->" + request.getRequestURI(),
                        KEY_IS_NOT_VALUABLE, "KEY IS NOT VALUABLE", start);
            }
        }


        responseDto.setElapsedTime(System.currentTimeMillis() - start);
        responseDtoService.createRdtoIndex(responseDto);
        return ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);

    }

    @Loggable
    @DeleteMapping("/mobiles/{mobile-no}/unblocking")
    public ResponseEntity<ResponseDto<String>> unBlock(
            @PathVariable("mobile-no") String mobileNo     //989176323629
            , @RequestHeader("description") String description
            , HttpServletRequest request) throws Exception {

//        log.info("##mobileNo = " + mobileNo + ", description = " + description);
        List<String> payload = new ArrayList<>();
        long start = System.currentTimeMillis();

        mobileNo= strongAuthService.correctMobileNo(mobileNo);
        if (!mobileNo.substring(0,2).equals("98"))
            throw new BadRequestAlertException(request.getMethod() + "-->" + request.getRequestURI(),
                    INVALID_MOBILE_NO, mobileNo, start);


        ResponseDto<String> responseDto = new ResponseDto<>(payload);
        responseDto.setPath(request.getMethod() + " " + request.getRequestURI());

        boolean isblocked = strongAuthService.isMobileBlock(mobileNo);
        if (isblocked) {
            strongAuthService.deleteFailedAttemptByMobileNo(mobileNo);
            responseDto.setHttpStatus(HttpStatus.OK.value());
            responseDto.setMessage("USER IS UNBLOCKED");
        } else {
            throw new BadRequestAlertException(request.getMethod() + "-->" + request.getRequestURI(),
                    USER_IS_NOT_BLOCKED, "USER IS NOT BLOCKED", start);

        }
        responseDto.setElapsedTime(System.currentTimeMillis() - start);
        responseDtoService.createRdtoIndex(responseDto);
        return ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);
    }


//    @GetMapping("{key}") // TODO Remove on Pro
//    public ResponseEntity<ResponseDto<String>> get(@PathVariable("key") String key, HttpServletRequest request) {
//        List<String> payload = new ArrayList<>();
//        ResponseDto<String> responseDto = new ResponseDto<>(payload);
//        responseDto.setPath(request.getMethod() + " " + request.getRequestURI());
//        payload.add(strongAuthService.get(key));
//        return ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);
//    }

//    @GetMapping("s/{id}")
//    public ResponseEntity<String> sget(@PathVariable("id") String id) {
//        return ResponseEntity.ok(strongAuthService.get("2", id));
//    }

//    @GetMapping("s/put/h")
//    public ResponseEntity<String> sputh() {
//        System.out.println("####PUT");
//        strongAuthService.put("2", "f", "v");
//        return ResponseEntity.ok("ADDED");
//    }

}
