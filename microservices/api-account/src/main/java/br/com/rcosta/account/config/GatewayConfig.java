package br.com.rcosta.account.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GatewayConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")  // Permite as origens do Angular e React Native
                .allowedMethods("*") // Métodos permitidos
                .allowedHeaders("*") // Permite todos os headers
                .allowCredentials(false);  // Permite envio de credenciais, se necessário (cookies, tokens, etc)
    }
}
