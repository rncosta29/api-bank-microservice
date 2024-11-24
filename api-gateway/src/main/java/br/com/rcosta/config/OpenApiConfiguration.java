package br.com.rcosta.config;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.providers.SpringDocProviders;
import org.springdoc.core.service.AbstractRequestService;
import org.springdoc.core.service.GenericResponseService;
import org.springdoc.core.service.OpenAPIService;
import org.springdoc.core.service.OperationService;
import org.springdoc.webflux.api.MultipleOpenApiWebFluxResource;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class OpenApiConfiguration {

	@Bean
	@Lazy(false)
	public List<GroupedOpenApi> apis(
			SwaggerUiConfigParameters config,
			RouteDefinitionLocator locator) {
		
		var definitions = locator.getRouteDefinitions().collectList().block();
		
		definitions.stream().filter(
					routeDefinition -> routeDefinition.getId()
						.matches(".*-ms"))
							.forEach(routeDefinition -> {
								String name = routeDefinition.getId();
								config.addGroup(name);
								GroupedOpenApi.builder()
									.pathsToMatch("/" + name + "/**")
									.group(name).build();
							}
				);
		return new ArrayList<>();
	}
	
	 @Bean
	 public MultipleOpenApiWebFluxResource multipleOpenApiResource(
	      List<GroupedOpenApi> groupedOpenApis,
	      ObjectFactory<OpenAPIService> defaultOpenAPIBuilder,
	      AbstractRequestService requestBuilder,
	      GenericResponseService responseBuilder,
	      OperationService operationParser,
	      SpringDocConfigProperties springDocConfigProperties,
	      SpringDocProviders springDocProviders) {
	    return new MultipleOpenApiWebFluxResource(
	        groupedOpenApis,
	        defaultOpenAPIBuilder,
	        requestBuilder,
	        responseBuilder,
	        operationParser,
	        springDocConfigProperties,
	        springDocProviders, null);
	  }
	
	public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                
                // Code version of the Gateway filter in the application.yml file keeping this around for reference.
//                .route("remote01",
//                        r -> r.path("/remote01/**").filters(
//                                f -> f
//                                // Apply the JWT Authentication
//                                .filter(new JwtAuthGatewayFilterFactory()
//                                        .apply(c -> c.setHeaderKey(authConfig.getJwt().getHeaderKey()).setIsEnabled("true")))
//                                // Strip the service name because we don't need it on the other end.
//                                .filter(new StripPrefixGatewayFilterFactory()
//                                        .apply(c -> c.setParts(1)))
//                                )
//                        .uri("lb://remote01"))
                .build();
    }
}
