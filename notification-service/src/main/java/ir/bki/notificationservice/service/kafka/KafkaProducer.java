package ir.bki.notificationservice.service.kafka;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 1/12/2022
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class KafkaProducer {

    private final Logger logger = Logger.getLogger("KafkaProducer");
    private static final String TOPIC = "sample_topic";
    private static final String TOPIC_BATCH = "sample_topic_batch";

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendMessage(String message) {
        this.kafkaTemplate.send(TOPIC, message);
        logger.info("#Published message: "+ message );
    }

    public void sendMessageBatch(String messageList) {
        this.kafkaTemplate.send(TOPIC_BATCH, messageList);
        logger.info("#Published TOPIC_BATCH message: "+messageList);
    }
}