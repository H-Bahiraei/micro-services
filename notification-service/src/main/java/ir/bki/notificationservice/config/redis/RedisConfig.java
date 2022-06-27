package ir.bki.notificationservice.config.redis;

import ir.bki.notificationservice.service.kafka.KafkaProducer;
import ir.bki.notificationservice.service.redis.RedisMessagePublisher;
import ir.bki.notificationservice.service.redis.RedisMessageSubscriberAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/5/2022
 */
@Configuration
@Slf4j
public class RedisConfig {

    @Autowired
    KafkaProducer kafkaProducer;
    @Value("${spring.redis.host}")
    private String redisServer;
    @Value("${spring.redis.port}")
    private String redisPort;
//    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {
//        String hostname = serviceConfig.getRedisServer();
//        int port = Integer.parseInt(serviceConfig.getRedisPort());
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(hostname, port);
//        //redisStandaloneConfiguration.setPassword(RedisPassword.of("yourRedisPasswordIfAny"));
//        return new JedisConnectionFactory(redisStandaloneConfiguration);
//    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        log.debug("#hostname: " + redisServer + " ;" + redisPort);
        String hostname = redisServer;
        int port = Integer.parseInt(redisPort);
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(hostname, port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of("Mani3280@"));
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
//        container.addMessageListener(messageListener(), topic());
        container.addMessageListener(messageListenerAdapter(), topic());
        return container;
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter() {
        log.info("###MessageListenerAdapter");
        return new RedisMessageSubscriberAdapter();
    }

//    @Bean
//    MessageListenerAdapter messageListener() {
//        log.info("###MessageListenerAdapter");
////        return new MessageListenerAdapter(new RedisMessageSubscriber());
//        return new MessageListenerAdapter(new RedisConsumerNew());
//    }

//    @Bean
//    MessageListener messageListener() {
//        log.info("###messageListener");
//        return new RedisMessageSubscriber();
//    }

    @Bean
    MessagePublisher redisPublisher() {
        return new RedisMessagePublisher(redisTemplate(), topic());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("messageQueue");
    }

//    @Bean
//    JedisConnectionFactory jedisConnectionFactory2() {
//        JedisConnectionFactory jedisConFactory
//                = new JedisConnectionFactory();
//        jedisConFactory.setHostName("localhost");
//        jedisConFactory.setPort(6379);
//        return jedisConFactory;
//    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }


//    @Bean
//    MessagePublisher redisPublisher() {
//        return new MessagePublisherImpl(redisTemplate(), topic());
//    }
}
