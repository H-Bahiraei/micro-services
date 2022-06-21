package ir.bki.notificationservice.controller.ibm;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import ir.bki.notificationservice.service.kafka.kafkaProducer;

@Component
@Slf4j
public class JmsReceiver {
//https://spring.io/guides/gs/messaging-jms/


    @Autowired
    private kafkaProducer kafkaProducer;

    @JmsListener(destination = "BATCH.IN", containerFactory = "myFactory")
    public void receiveMessage(String msg) {

        log.info("MQ Received: " + msg );
        //TODO put it in kafka
        kafkaProducer.sendMessage(msg);

    }

}