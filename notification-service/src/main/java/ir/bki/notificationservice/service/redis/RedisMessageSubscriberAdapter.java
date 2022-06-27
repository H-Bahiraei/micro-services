package ir.bki.notificationservice.service.redis;

import ir.bki.notificationservice.service.kafka.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;

/**
 * @author H-Bahiraei
 * Created on 6/27/2022
 */

@Service
@Slf4j
public class RedisMessageSubscriberAdapter extends MessageListenerAdapter {


    @Autowired
    private KafkaProducer KafkaProducer;

//    @Autowired
//    public RedisMessageSubscriber(KafkaProducer KafkaProducer) {
//        this.KafkaProducer = KafkaProducer;
//    }
//    @Autowired
//    public RedisMessageSubscriber(KafkaProducer KafkaProducer) {
//        this.KafkaProducer = KafkaProducer;
//    }

    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        log.info("#RedisMessageSubscriber received message" +message);
        byte[] messageBody = message.getBody();
        String messDto=String.valueOf(SerializationUtils.deserialize(messageBody));
        KafkaProducer.sendMessage(messDto); // to kafka
        log.info("#Message received: " + new String(message.getBody()));
    }
}
