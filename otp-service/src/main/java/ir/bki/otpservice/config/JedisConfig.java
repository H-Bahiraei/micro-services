package ir.bki.otpservice.config;

import ir.bki.otpservice.listener.MessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/5/2022
 */
@Configuration
@Slf4j
public class JedisConfig {

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
        log.debug("#hostname: "+redisServer+" ;"+redisPort);
        String hostname = redisServer;
        int port = Integer.parseInt(redisPort);
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(hostname, port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of("Mani3280@"));
        return new JedisConnectionFactory(redisStandaloneConfiguration);
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
