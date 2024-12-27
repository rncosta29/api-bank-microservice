package br.com.rcosta.config;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

	@Bean
	public List<GroupedOpenApi> apis(SwaggerUiConfigParameters config, RouteDefinitionLocator locator) {
	    var definitions = locator.getRouteDefinitions().collectList().block();
	    List<GroupedOpenApi> groupedOpenApis = new ArrayList<>();

	    definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*"))
	        .forEach(routeDefinition -> {
	            String name = routeDefinition.getId();
	            config.addGroup(name);  // Adiciona o grupo ao Swagger UI

	            // O caminho para o Swagger dos microserviços
	            String swaggerUrl = "/v3/api-docs"; // Alterado para corrigir a URL

	            // Configuração do Swagger UI para o microserviço
	            GroupedOpenApi groupedOpenApi = GroupedOpenApi.builder()
	                .pathsToMatch("/" + name + "/**")
	                .group(name)
	                .addOpenApiCustomizer(openApi -> openApi.setServers(
	                    List.of(new io.swagger.v3.oas.models.servers.Server().url(swaggerUrl)))).build();

	            groupedOpenApis.add(groupedOpenApi);
	        });

	    return groupedOpenApis;
	}

}
