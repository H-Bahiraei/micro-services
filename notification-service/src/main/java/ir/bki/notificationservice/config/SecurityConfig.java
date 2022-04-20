package ir.bki.notificationservice.config;


import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

//https://www.keycloak.org/2017/05/easily-secure-your-spring-boot.html
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true) // enabled @RolesAllowed
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .authorizeRequests()
//                .antMatchers("/mobiles*")
////                .anyRequest()
//                .authenticated();
//https://github.com/eugenp/tutorials/blob/master/spring-boot-modules/spring-boot-swagger-keycloak/src/main/java/com/baeldung/swaggerkeycloak/GlobalSecurityConfig.java
                // we can set up authorization here alternatively to @Secured methods
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/api/**").authenticated()
                // force authentication for all requests (and use global method security)
                .anyRequest().permitAll();
        http
                .csrf()
                .disable();
    }

    //In the code above, the method configureGlobal() tasks the SimpleAuthorityMapper to make sure roles are not prefixed with ROLE_.
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    //Define a session authentication strategy
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    GrantedAuthoritiesMapper authoritiesMapper() {
        SimpleAuthorityMapper mapper = new SimpleAuthorityMapper();
        mapper.setPrefix("ROLE_"); // Spring Security adds a prefix to the authority/role names (we use the default here)
        mapper.setConvertToUpperCase(true); // convert names to uppercase
        mapper.setDefaultAuthority("ROLE_ANONYMOUS"); // set a default authority
        return mapper;
    }

}
