package br.com.rcosta.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Configuration
public class CorsConfig implements GlobalFilter, Ordered {

	@Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getResponse().getHeaders();

        // Permite qualquer origem
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "false"); // Não permite credenciais

        // Verifica se é uma requisição preflight (OPTIONS)
        if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
            exchange.getResponse().setStatusCode(HttpStatus.OK); // Retorna OK para pré-verificação
            return Mono.empty();
        }

        // Continua a execução para outras requisições
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE; // Alta prioridade
    }
}
