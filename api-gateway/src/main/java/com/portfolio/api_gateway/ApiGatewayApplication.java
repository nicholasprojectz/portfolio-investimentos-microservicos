package com.portfolio.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    // IGNORANDO O YAML: Esta classe força o Spring a criar a rota na memória.
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service-java", r -> r.path("/auth/**")
                        .uri("http://localhost:8081"))
                .route("portfolio-service-java", r -> r.path("/api/portfolio/**")
                        .uri("http://localhost:8082"))
                .build();
    }
}
