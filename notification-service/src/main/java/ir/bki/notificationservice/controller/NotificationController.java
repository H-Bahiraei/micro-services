package ir.bki.notificationservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import ir.bki.notificationservice.apects.LoggableHTTP;
import ir.bki.notificationservice.dto.*;
import ir.bki.notificationservice.log.LoggerNamesEnum;
import ir.bki.notificationservice.service.client.MagfaFeignClient;
import ir.bki.notificationservice.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
public class NotificationController {
    private static final Logger MAGFA_LOGGER = LoggerFactory.getLogger(LoggerNamesEnum.magfa.getDesc());

    private final MagfaFeignClient magfaFeignClient;

    @Value("${magfa.url}")
    private String url;

    @Value("${magfa.test-receiver-list}")
    private List<String> testReceivers;

    @Value("${magfa.number}")
    private String magfaNumber;

    public NotificationController(MagfaFeignClient magfaFeignClient) {
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

    public ResponseEntity buildFallbackBalance(String name, Throwable ex) {
        System.err.println("####Rate limit applied no further calls are accepted " + name + " ;ex: " + ex.getMessage());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Retry-After", "1"); //retry after one second

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .headers(responseHeaders) //send retry header
                .body("Too many request - No further calls are accepted");
    }

    @LoggableHTTP
    @PostMapping("/notification/{mobile-no}")
    public ResponseDto sendOne(@PathVariable("mobile-no") String mobileNo
            , @RequestBody NotificationRequestDto notificationRequestDto) { //, @QueryParam("provider") String provider
        MAGFA_LOGGER.info("mobileNo = " + mobileNo + ", notificationRequestDto = " + notificationRequestDto.getMessage());

//        notificationRequestDto.setMobileNo(mobileNo);
//        return sendBatch(List.of(notificationRequestDto));
        //        return sendBatch(Collections.singletonList(notificationRequestDto));

        List<String> payload = new ArrayList<>();
        ResponseDto<String> responseDto = new ResponseDto<>(payload);

        MagfaRequestDto magfaDto =  MagfaRequestDto.builder()
                .recipients(List.of(mobileNo))
                .senders(List.of(magfaNumber))
                .build();

        magfaDto.setMessages(List.of(notificationRequestDto.getMessage()));
        MagfaResponseDto magfaResponseDto = magfaFeignClient.send(magfaDto);
        MagfaMessageDto magfaMessageDto;
        if (magfaResponseDto != null && magfaResponseDto.getMessages() != null) {
            magfaMessageDto = magfaResponseDto.getMessages().get(0);
            payload.add(magfaMessageDto.getId() + "");
        }
        MAGFA_LOGGER.debug("#magfaDto1: " + magfaResponseDto);
        if (magfaResponseDto != null) {
            responseDto.setStatusCode(magfaResponseDto.getStatus());
            if (magfaResponseDto.getStatus() == 0)
                responseDto.setMessage("موفق");
        } else {
            responseDto.setStatusCode(50001);
            responseDto.setMessage("Cant connect to magfa! " + url);
        }
        return responseDto;

    }

    @LoggableHTTP
    @PostMapping("/notification")
    public ResponseDto<MagfaMessageDto> sendBatch(@RequestBody List<NotificationRequestDto> notificationRequestDtos) { //, @QueryParam("provider") String provider
        log.info("#sendBatch to MAGFA-> notificationRequestDtos = " + notificationRequestDtos);
//        MAGFA_LOGGER.info("mobileNo = " + notificationRequestDto.getMobilesMessages() + ", notificationRequestDto = " + notificationRequestDto);

        List<String> mobilesList = new ArrayList();
        List<String> messagesList = new ArrayList();
        List<String> senders = new ArrayList();
        List<String> uids = new ArrayList();
        List<String> encodings = new ArrayList();
        List<String> udhs = new ArrayList();

        for (NotificationRequestDto requestDto : notificationRequestDtos) {
            mobilesList.add(requestDto.getMobileNo());
            messagesList.add(requestDto.getMessage());
            senders.add(magfaNumber);
            uids.add(requestDto.getUid());
            encodings.add(requestDto.getEncoding());
            udhs.add(requestDto.getUdh());
        }

        List<MagfaMessageDto> payload = new ArrayList<>();
        ResponseDto<MagfaMessageDto> responseDto = new ResponseDto<>(payload);

        MagfaRequestDto magfaRequestDto = MagfaRequestDto.builder()
                .messages(messagesList)
                .recipients(mobilesList)
                .encodings(encodings)
                .senders(senders)
                .uids(uids)
                .udhs(udhs)
                .build();

        MagfaResponseDto magfaResponseDto = magfaFeignClient.send(magfaRequestDto);

//        MagfaMessageDto magfaMessageDto;
//        if (magfaResponseDto != null && magfaResponseDto.getMessages() != null) {
//            magfaMessageDto = magfaResponseDto.getMessages().get(0);
//            payload.add(magfaMessageDto.getId() + "");
//        }

        MAGFA_LOGGER.debug("#magfaResponseDto: " + magfaResponseDto);
        if (magfaResponseDto.getMessages() != null) {
            // ???
            payload.addAll(magfaResponseDto.getMessages());
            responseDto.setStatusCode(magfaResponseDto.getStatus());
            if (magfaResponseDto.getStatus() == 0)
                responseDto.setMessage("موفق");
        } else {
            responseDto.setStatusCode(50001);
            responseDto.setMessage("Cant connect to magfa! " + url);
        }
        return responseDto;
    }

//    @GetMapping("send")
//    public MagfaResponseDto send() {
//        MagfaRequestDto magfaDto = new MagfaRequestDto();
//        magfaDto.setRecipients(testReceivers);
//        magfaDto.setSenders(List.of(magfaNumber));
//        magfaDto.setMessages(List.of("سلام Notification service"));
//        MagfaResponseDto magfaDto1 = magfaFeignClient.send(magfaDto);
//        System.out.println("#magfaDto1: " + magfaDto1);
//        return magfaFeignClient.send(magfaDto);
//    }

    @RequestMapping(value = "/test/{mobile-no}", method = RequestMethod.GET)
    public ResponseEntity<String> getLicenses(@PathVariable("mobile-no") String mobileNo) throws TimeoutException {
        log.debug("LicenseServiceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return getLicensesByOrganization(mobileNo);
    }

    @CircuitBreaker(name = "magfaSendSms", fallbackMethod = "buildFallbackLicenseList")
    @RateLimiter(name = "rateLimitMagfaSendSms", fallbackMethod = "buildFallbackLicenseList")
    @Retry(name = "retryMagfaSendSms", fallbackMethod = "buildFallbackLicenseList")
    @Bulkhead(name = "bulkheadMagfaSendSms", type = Bulkhead.Type.THREADPOOL, fallbackMethod = "buildFallbackLicenseList")
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
