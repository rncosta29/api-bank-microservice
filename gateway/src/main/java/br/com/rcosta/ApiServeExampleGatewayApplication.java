package br.com.rcosta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import br.com.rcosta.config.CorsConfig;

@SpringBootApplication
@Import(CorsConfig.class)
public class ApiServeExampleGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiServeExampleGatewayApplication.class, args);
	}

}
