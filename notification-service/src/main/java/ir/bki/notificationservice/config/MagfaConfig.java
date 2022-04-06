package ir.bki.notificationservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/4/2022
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "magfa")
public class MagfaConfig {
    private String url;
    private String number;
    private String username;
    private String password;
}
