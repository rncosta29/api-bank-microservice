package br.com.rcosta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ApiServeExampleServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiServeExampleServerApplication.class, args);
	}

}
