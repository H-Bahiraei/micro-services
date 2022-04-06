package ir.bki.otpservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableEurekaClient
public class OtpServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OtpServiceApplication.class, args);
    }

//	@Bean
//	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
//											MessageListenerAdapter listenerAdapter) {
//
//		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
//
//		return container;
//	}
//
//	@Bean
//	MessageListenerAdapter listenerAdapter(Receiver receiver) {
//		return new MessageListenerAdapter(receiver, "receiveMessage");
//	}
//
//	@Bean
//	Receiver receiver() {
//		return new Receiver();
//	}
//
//	@Bean
//	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
//		return new StringRedisTemplate(connectionFactory);
//	}
//
//	public static void main(String[] args) throws InterruptedException {
//
//		ApplicationContext ctx = SpringApplication.run(MessagingRedisApplication.class, args);
//
//		StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
//		Receiver receiver = ctx.getBean(Receiver.class);
//
//		while (receiver.getCount() == 0) {
//
//			LOGGER.info("Sending message...");
//			template.convertAndSend("chat", "Hello from Redis!");
//			Thread.sleep(500L);
//		}
//
//		System.exit(0);
//	}

}
