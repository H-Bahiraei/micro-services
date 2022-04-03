package ir.bki.notificationservice.config;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 3/27/2022
 */
@Configuration
public class KeycloakConfig {
    //keycloakConfigResolver defines that we want to use the Spring Boot properties file support instead of the default keycloak.json.
    //Be default, the Spring Security Adapter looks for a keycloak.json file
    /*
     * re-configure keycloak adapter for Spring Boot environment,
     * i.e. to read config from application.yml
     * (otherwise, we need a keycloak.json file)
     */
    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
}