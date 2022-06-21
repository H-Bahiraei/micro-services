package ir.bki.notificationservice.service.kafka;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 1/12/2022
 */

import com.google.gson.reflect.TypeToken;
import ir.bki.notificationservice.controller.NotificationController;
import ir.bki.notificationservice.dto.NotificationRequestDto;
import ir.bki.notificationservice.utils.JSONFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

@Service
public class KafkaConsumer {

    private final Logger logger = Logger.getLogger("KafkaConsumer");
    @Autowired
    private NotificationController notificationController;


    @KafkaListener(topics = "sample_topic", groupId = "group_id")
    public void consume(String message) {
        logger.info("#Consume message: " + message);
        NotificationRequestDto notificationRequestDto = JSONFormatter.fromJSON(message, NotificationRequestDto.class); // Error Handling...
        notificationController.sendOne(notificationRequestDto.getMobileNo(), notificationRequestDto);
    }

    @KafkaListener(topics = "sample_topic_batch", groupId = "group_id")
    public void consumeBatch(String messageList) {
        logger.info("#Consume Batch message: " + messageList);
        List<NotificationRequestDto> notificationRequestDto = null; // TODO Error Handling...
        try {
            Type listOfTCIBillType = new TypeToken<List<NotificationRequestDto>>() {}.getType();
            notificationRequestDto = JSONFormatter.fromJSON(messageList, listOfTCIBillType);
        } catch (Exception ex) {
            logger.warning(" exception in deserializing bill Object" + ex.getMessage());
        }
        logger.info("#Consume Batch notificationRequestDto: " + notificationRequestDto);
        notificationController.sendBatch(notificationRequestDto);
    }
}