package br.com.rcosta.account.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

	@Bean
    public GroupedOpenApi apiAccount() {
        return GroupedOpenApi.builder()
                .group("api-account")
                .pathsToMatch("/api-account/**")
                .build();
    }
	
	@Bean
    public GroupedOpenApi apiBank() {
        return GroupedOpenApi.builder()
                .group("api-bank")
                .pathsToMatch("/api-bank/**")
                .build();
    }
}
