//package ir.bki.notificationservice.controller.kafka;
//
//import ir.bki.notificationservice.dto.NotificationRequestDto;
//import ir.bki.notificationservice.service.kafka.KafkaProducer;
//import ir.bki.notificationservice.utils.JSONFormatter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * @author Mahdi Sharifi
// * @version 2022.1.1
// * https://www.linkedin.com/in/mahdisharifi/
// * @since 1/12/2022
// */
//
//@RestController
//@RequestMapping(value = "/kafka")
//@Slf4j
//public class KafkaController {
//
//
//    private final KafkaProducer kafkaProducer;
//
//    public KafkaController(KafkaProducer kafkaProducer) {
//        this.kafkaProducer = kafkaProducer;
//    }
//
//    @PostMapping(value = "/publishMessage") //http://localhost:8082/kafka/publish?message=HHHHHHHHHH
//    public void pubsubToKafkaTopic(@RequestBody NotificationRequestDto notificationRequestDto) {
////        log.debug("#notificationRequestDto = " + notificationRequestDto);
//        this.kafkaProducer.sendMessage(notificationRequestDto.toJSON());
//    }
//
//    @PostMapping(value = "/publishMessages") //http://localhost:8082/kafka/publish?message=HHHHHHHHHH
//    public void pubsubToKafkaTopicBatch(@RequestBody List<NotificationRequestDto> notificationRequestDtos) {
////        log.info("#notificationRequestDtos Batch= " + notificationRequestDtos);
//        this.kafkaProducer.sendMessageBatch(JSONFormatter.toJSON(notificationRequestDtos));
//    }
//
//
//
//
//
//
////    @GetMapping(value = "/publish") //http://localhost:8082/kafka/publish?message=HHHHHHHHHH
////    public void sendMessageToKafkaTopic(@RequestParam String message) {
////        this.KafkaProducer.sendMessage(message);
////    }
//}