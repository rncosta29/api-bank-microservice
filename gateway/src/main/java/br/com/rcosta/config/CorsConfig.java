package br.com.rcosta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

	@Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:4200"); // Permitir a origem do Angular
        config.addAllowedMethod("*"); // Permitir todos os métodos (GET, POST, etc.)
        config.addAllowedHeader("*"); // Permitir todos os headers
        config.setAllowCredentials(true); // Permitir cookies ou credenciais

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Aplicar a todas as rotas

        return new CorsWebFilter(source);
    }
}
