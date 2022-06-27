//package ir.bki.notificationservice.service.redis;
//
//import ir.bki.notificationservice.dto.NotificationRequestDto;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import   ir.bki.notificationservice.service.kafka.KafkaProducer;
//
//
//@Service
//@Slf4j
//public class RedisMessageSubscriber implements MessageListener {
//
//
//    @Autowired
//    private KafkaProducer KafkaProducer;
//
////    @Autowired
////    public RedisMessageSubscriber(KafkaProducer KafkaProducer) {
////        this.KafkaProducer = KafkaProducer;
////    }
////    @Autowired
////    public RedisMessageSubscriber(KafkaProducer KafkaProducer) {
////        this.KafkaProducer = KafkaProducer;
////    }
//
//    @Override
//    public void onMessage(final Message message, final byte[] pattern) {
//        log.info("#RedisMessageSubscriber received message" +message);
//        // TODO put it in kafka
////        ir.bki.notificationservice.service.kafka.KafkaProducer KafkaProducer = new ir.bki.notificationservice.service.kafka.KafkaProducer();
//        NotificationRequestDto notificationRequestDto=new NotificationRequestDto();
//        notificationRequestDto.setMessage(message.toString());
//        KafkaProducer.sendMessage(notificationRequestDto.toJSON());
//
//        log.info("#Message received: " + new String(message.getBody()));
//    }
//}
