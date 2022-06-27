package ir.bki.notificationservice.controller.redis;

import ir.bki.notificationservice.dto.NotificationRequestDto;
import ir.bki.notificationservice.service.kafka.KafkaProducer;
import ir.bki.notificationservice.service.redis.RedisMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/redis")
@Slf4j
public class RedisController {

    @Autowired
    RedisMessagePublisher redisMessagePublisher;

    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping(value = "/publishMessage") //http://localhost:8082/kafka/publish?message=HHHHHHHHHH
    public void sendMessageToRedis(@RequestBody NotificationRequestDto notificationRequestDto ) {
        log.info("# redis message = " + notificationRequestDto.toJSON());
        redisMessagePublisher.publish(notificationRequestDto.toJSON()); // TODO :  a third_party copy this code
//        listOps.leftPush("1",message);
    }

}
