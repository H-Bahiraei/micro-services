//package ir.bki.notificationservice.controller;
//
//import ir.bki.notificationservice.log.LoggerNamesEnum;
//import ir.bki.notificationservice.service.client.RahyabFeignClient;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * @author Mohsen Sabbaghi
// * @version 2022.1.1
// * https://www.linkedin.com/in/sabbaghi/
// * @since 4/3/2022
// */
//
//@RestController
//@RequestMapping("v1/sms")
//@Slf4j
//public class RahyabSmsController {
//
////    private static final Logger RAHYAB_LOGGER = LoggerFactory.getLogger(LoggerNamesEnum.RAHYAB.getDesc());
//
//    private final RahyabFeignClient rahyabFeignClient;
//
//    @Value("${rahyab.url}")
//    private String url;
//
//    @Value("${rahyab.test-receiver-list}")
//    private List<String> testReceivers;
//
//    @Value("${rahyab.from}")
//    private List<String> rahyabNumbersList;
//
//    public RahyabSmsController(RahyabFeignClient rahyabFeignClient) {
//        this.rahyabFeignClient = rahyabFeignClient;
//    }
//
//    @GetMapping("/ip")
//    public ResponseEntity<String> ipEnquiry() {
//        return ResponseEntity.ok(rahyabFeignClient.ipEnquiry());
//    }
//
//}
