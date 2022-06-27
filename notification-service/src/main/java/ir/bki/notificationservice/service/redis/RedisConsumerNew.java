//package ir.bki.notificationservice.service.redis;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//
//@Service
//@Slf4j
//public class RedisConsumerNew implements MessageListener {
//    private static final String TOPIC = "sample_topic";
////    @Autowired
////    private ir.bki.notificationservice.service.kafka.KafkaProducer KafkaProducer;
//    @Autowired
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//
//        log.info("###RedisConsumerNew message: "+message);
//        this.kafkaTemplate.send(TOPIC, "message");
//        log.info("#Published message: "+message);
//    }
//}
