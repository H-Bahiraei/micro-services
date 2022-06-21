package ir.bki.notificationservice.service.redis;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import ir.bki.notificationservice.service.kafka.kafkaProducer;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RedisMessageSubscriber implements MessageListener {
    public static List<String> messageList = new ArrayList<String>();

//    private final kafkaProducer kafkaProducer;
//    public RedisMessageSubscriber(kafkaProducer kafkaProducer) {
//        this.kafkaProducer = kafkaProducer;
//    }

    @Autowired
    private kafkaProducer kafkaProducer;

    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        // TODO put it in kafka
        kafkaProducer.sendMessage(message.toString());
//        messageList.add(message.toString()); //
        log.info("#Message received: " + new String(message.getBody()));
    }
}
