package ir.bki.notificationservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import ir.bki.notificationservice.dto.*;
import ir.bki.notificationservice.service.client.MagfaFeignClient;
import ir.bki.notificationservice.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/3/2022
 */
//https://reflectoring.io/rate-limiting-with-springboot-resilience4j/
@RestController
@RequestMapping("v1/sms")
@Slf4j
public class MagfaSmsController {

    private final MagfaFeignClient magfaFeignClient;

    @Value("${magfa.url}")
    private String url;

    @Value("${magfa.test-receiver-list}")
    private List<String> testReceivers;

    @Value("${magfa.number}")
    private String magfaNumber;

    public MagfaSmsController(MagfaFeignClient magfaFeignClient) {
        this.magfaFeignClient = magfaFeignClient;
    }

    @GetMapping("/balance")
    @RateLimiter(name = "rateLimitMagfaGetBalance")//, fallbackMethod = "buildFallbackBalance"
    @CircuitBreaker(name = "magfaSendSms")
    public ResponseEntity getBalance() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(magfaFeignClient.getBalance());
    }

    public ResponseEntity buildFallbackBalance( String name, Throwable ex) {
        System.err.println("####Rate limit applied no further calls are accepted "+name+" ;ex: "+ex.getMessage());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Retry-After", "1"); //retry after one second

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .headers(responseHeaders) //send retry header
                .body("Too many request - No further calls are accepted");
    }
    @PostMapping("/mobiles/{mobile-no}")
    public ResponseEntity<ResponseDto<String>> sendOne(@PathVariable("mobile-no") String mobileNo
            , @RequestBody NotificationRequestDto notificationRequestDto) { //, @QueryParam("provider") String provider
        System.err.println("####mobileNo = " + mobileNo + ", notificationRequestDto = " + notificationRequestDto);
        List<String> payload = new ArrayList<>();
        ResponseDto<String > responseDto=new ResponseDto<>(payload);

        MagfaRequestDto magfaDto = new MagfaRequestDto();
        magfaDto.setRecipients(List.of(mobileNo));
        magfaDto.setSenders(List.of(magfaNumber));

        magfaDto.setMessages(List.of(notificationRequestDto.getMessage()));
        MagfaDto magfaDto1 = magfaFeignClient.send(magfaDto);
        MagfaMessageDto magfaMessageDto=null;
        if(magfaDto1!=null && magfaDto1.getMessages()!=null){
            magfaMessageDto=magfaDto1.getMessages().get(0);
            payload.add(magfaMessageDto.getId()+"");
        }
        System.out.println("#magfaDto1: " + magfaDto1);
        if(magfaDto1!=null) {
            responseDto.setStatusCode(magfaDto1.getStatus());
            if(magfaDto1.getStatus()==0)
                responseDto.setMessage("موفق");
        }
        else {
            responseDto.setStatusCode(50001);
            responseDto.setMessage("Cant connect to magfa! "+url);
        }
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("send")
    public MagfaDto send() {
        MagfaRequestDto magfaDto = new MagfaRequestDto();
        magfaDto.setRecipients(testReceivers);
//        magfaDto.setRecipients(List.of("09133480144", "989124402951"));
        magfaDto.setSenders(List.of(magfaNumber));
        magfaDto.setMessages(List.of("سلام بچه ها"));
        MagfaDto magfaDto1 = magfaFeignClient.send(magfaDto);
        System.out.println("#magfaDto1: " + magfaDto1);
        return magfaFeignClient.send(magfaDto);
    }

    @RequestMapping(value = "/test/{mobile-no}", method = RequestMethod.GET)
    public ResponseEntity<String> getLicenses(@PathVariable("mobile-no") String mobileNo) throws TimeoutException {
        log.debug("LicenseServiceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return getLicensesByOrganization(mobileNo);
    }

    @CircuitBreaker(name = "magfaSendSms", fallbackMethod = "buildFallbackLicenseList")
    @RateLimiter(name = "rateLimitMagfaSendSms", fallbackMethod = "buildFallbackLicenseList")
    @Retry(name = "retryMagfaSendSms", fallbackMethod = "buildFallbackLicenseList")
    @Bulkhead(name = "bulkheadMagfaSendSms", type= Bulkhead.Type.THREADPOOL, fallbackMethod = "buildFallbackLicenseList")
    public ResponseEntity<String> getLicensesByOrganization(String mobileNo) throws TimeoutException {
//        if("09133480144".equals(mobileNo))
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        log.debug("getLicensesByOrganization Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        randomlyRunLong();
        return ResponseEntity.ok("everything is OK");
    }

    @SuppressWarnings("unused")
    private ResponseEntity<String> buildFallbackLicenseList(String mobileNo, Throwable t) {
        System.err.println("###########FAILURE");
        List<String> fallbackList = new ArrayList<>();
//        License license = new License();
//        license.setLicenseId("0000000-00-00000");
//        license.setOrganizationId(organizationId);
//        license.setProductName("Sorry no licensing information currently available");
        fallbackList.add(mobileNo);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    private void randomlyRunLong() {
//         sleep();
    }

    private void sleep() {

        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }


}
