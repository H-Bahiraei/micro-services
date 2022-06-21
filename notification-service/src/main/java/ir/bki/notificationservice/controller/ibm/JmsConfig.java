package ir.bki.notificationservice.controller.ibm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
//  https://spring.io/guides/gs/messaging-jms/
@Configuration
@Slf4j
public class JmsConfig {
    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        log.error("#JmsListenerContainerFactory: "+connectionFactory);
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        return factory;
    }

//    @Bean // Serialize message content to json using TextMessage
//    public MessageConverter jacksonJmsMessageConverter() {
//        log.error("#jacksonJmsMessageConverter ");
////        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
////        converter.setTargetType(MessageType.TEXT);
////        converter.setTypeIdPropertyName("_type");
//        return converter;
//    }

}
