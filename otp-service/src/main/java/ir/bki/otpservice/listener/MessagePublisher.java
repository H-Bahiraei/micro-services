package ir.bki.otpservice.listener;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/5/2022
 */
//https://dzone.com/articles/intro-to-redis-with-spring-boot
public interface MessagePublisher {

    void publish(final String message);
}
