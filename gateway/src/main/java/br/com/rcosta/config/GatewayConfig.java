package br.com.rcosta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GatewayConfig {

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); // Permite envio de cookies, se necessário
		config.addAllowedOriginPattern("*"); // Permite todas as origens (ou especifique as permitidas)
		config.addAllowedHeader("*"); // Permite todos os headers
		config.addAllowedMethod("*"); // Permite todos os métodos HTTP

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config); // Aplica a configuração para todos os endpoints

		return new CorsFilter(source);
	}
}
