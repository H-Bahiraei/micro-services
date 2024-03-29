//package ir.bki.notificationservice.service.ibm;
//
//import ir.bki.notificationservice.service.kafka.KafkaProducer;
//import lombok.extern.slf4j.Slf4j;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.stereotype.Service;
//
///**
// * @author Mahdi Sharifi
// * @version 2022.1.1
// * https://www.linkedin.com/in/mahdisharifi/
// * @since 1/12/2022
// */
//
//@Service
//@Slf4j
//public class IBMJmsReceiver {
////https://spring.io/guides/gs/messaging-jms/
//
//    @Autowired
//    private KafkaProducer kafkaProducer;
//
////    @JmsListener(destination = "BATCH.IN", containerFactory = "myFactory")
//    public void receiveMessage(String msg) {
//        log.info("####MQ Received: " + msg );
//        log.info("");
//        kafkaProducer.sendMessage(msg); // to kafka
//    }
//
//}
//
