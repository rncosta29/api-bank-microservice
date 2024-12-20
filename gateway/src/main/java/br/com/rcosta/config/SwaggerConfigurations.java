package br.com.rcosta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfigurations {

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("API para cadastro de Contas")
				.version("v1")
				.description("Controle e cadastro de contas")
				.termsOfService("https://www.linkedin.com/in/rafael-neres-da-costa/")
				.license(
					new License()
						.name("Apache 2.0")
						.url("https://www.linkedin.com/in/rafael-neres-da-costa/")
					)
				);
	}
}
