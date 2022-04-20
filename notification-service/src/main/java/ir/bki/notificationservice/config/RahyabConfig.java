package ir.bki.notificationservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mohsen Sabbaghi
 * @version 2022.1.1
 * https://www.linkedin.com/in/sabbaghi/
 * @since 4/4/2022
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "rahyab")
public class RahyabConfig {
    private String url;
    private String from;
    private String to;
    private String message;
    private String username;
    private String password;
}
