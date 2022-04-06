package ir.bki.notificationservice.config.feing;

import feign.Logger;
import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import feign.okhttp.OkHttpClient;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/3/2022
 */
@Configuration
public class FeignClientConfiguration {
    @Value("${magfa.username}")
    String magfaUsername;
    @Value("${magfa.password}")
    String magfaPassword;
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(magfaUsername, magfaPassword);
    }
    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL ;
    }

//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return requestTemplate -> {
//            requestTemplate.header("Accept", ContentType.APPLICATION_JSON.getMimeType()+"; charset==UTF-8");
//        };
//    }

//    @Bean
//    public OkHttpClient client() {
//        return new OkHttpClient();
//    }
//
//    @Bean
//    Logger.Level feignLoggerLevel() {
//        return Logger.Level.FULL;
//    }
//
//    @Bean
//    public Decoder feignDecoder() {
//        return new JacksonDecoder();
//    }
//
//    @Bean
//    public Encoder feignFormEncoder () {
//        return new SpringFormEncoder(new JacksonEncoder());
//    }
}