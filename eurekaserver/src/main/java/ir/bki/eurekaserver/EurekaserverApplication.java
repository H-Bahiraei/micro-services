package ir.bki.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaserverApplication extends SpringBootServletInitializer{ // for get warFile

	public static void main(String[] args) {
		SpringApplication.run(EurekaserverApplication.class, args);
	}

}
