package ir.bki.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GatewayserverApplication {
     // Impirtant
    public static void main(String[] args) {
        System.out.println("Starting Config server.....");
        SpringApplication.run(GatewayserverApplication.class, args);
    }

}
