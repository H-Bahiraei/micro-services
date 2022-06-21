package ir.bki.otpservice.service.kafka;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 1/12/2022
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class KafkaProducer {

    private final Logger logger = Logger.getLogger("KafkaProducer");
    private static final String TOPIC = "sample_topic";
    private static final String TOPIC_BATCH = "sample_topic_batch";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public void sendMessage(String message) {
        this.kafkaTemplate.send(TOPIC, message);
        logger.info("#Published message: "+message);
    }

    public void sendMessageBatch(String messageList) {
        this.kafkaTemplate.send(TOPIC_BATCH, messageList);
        logger.info("#Published TOPIC_BATCH message: "+messageList);
    }
}