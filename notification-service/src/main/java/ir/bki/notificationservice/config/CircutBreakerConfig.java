package ir.bki.notificationservice.config;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/4/2022
 */

//@Configuration
//public class CircutBreakerConfig {
//    @Bean
//    public CircuitBreakerConfigCustomizer circuitBreakerConfigCustomizer() {
//        return CircuitBreakerConfigCustomizer.of("cb-instanceB",builder -> builder.minimumNumberOfCalls(10)
//                .permittedNumberOfCallsInHalfOpenState(15));
//    }
//}
