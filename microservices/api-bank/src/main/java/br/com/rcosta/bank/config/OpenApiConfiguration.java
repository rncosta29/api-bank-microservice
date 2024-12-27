package br.com.rcosta.bank.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
	
	@Bean
    public GroupedOpenApi apiBank() {
        return GroupedOpenApi.builder()
                .group("api-bank")
                .pathsToMatch("/api-bank/**")
                .build();
    }
}
