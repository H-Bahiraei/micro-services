package ir.bki.otpservice.config.OpenApi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/30/22
 * Change Swagger Header UI
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("OTP Service")
                        .description("generate and verify Otp Code")
                        .version("v1")
                        .contact(new Contact()
                                .name("Mahdi Sharifi")
                                .url("https://www.linkedin.com/in/mahdisharifi/")
                                .email("mahdi.elu@gmail.com"))
                );
    }
}
