package br.com.rcosta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.WebFilter;

@Configuration
public class CorsGlobalConfiguration {

	@Bean
    public WebFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        source.registerCorsConfiguration("/**", config);
        return (exchange, chain) -> {
            if (exchange.getRequest().getMethod() != null) {
                exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "*");
            }
            return chain.filter(exchange);
        };
    }
}
