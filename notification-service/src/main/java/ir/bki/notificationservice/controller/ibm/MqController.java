package ir.bki.notificationservice.controller.ibm;

import ir.bki.notificationservice.dto.NotificationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

//https://developer.ibm.com/tutorials/mq-jms-application-development-with-spring-boot/
@RestController
@RequestMapping(value = "/mq")
@EnableJms
public class MqController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @GetMapping("/publishMessage")
    public String send(@RequestBody NotificationRequestDto notificationRequestDto ) {
        try {
            jmsTemplate.convertAndSend("BATCH.IN", notificationRequestDto.toJSON()); // TODO :  a third_party copy this code
            return "OK";
        } catch (JmsException ex) {
            ex.printStackTrace();
            return "FAIL";
        }
    }

    @GetMapping("/receiveMessage")
    public String receive() {
        try {
            return jmsTemplate.receiveAndConvert("BATCH.IN").toString();
        } catch (JmsException ex) {
            ex.printStackTrace();
            return "FAIL";
        }
    }
}
