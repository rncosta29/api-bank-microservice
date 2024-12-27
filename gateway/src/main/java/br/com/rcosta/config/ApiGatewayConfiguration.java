package br.com.rcosta.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
    RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
    	return builder.routes()
        		.route(p -> p.path("/API-ACCOUNT/**").uri("lb://api-account"))
        		.route(p -> p.path("/API-BANK/**").uri("lb://api-bank"))
        		.build();
    }
}
