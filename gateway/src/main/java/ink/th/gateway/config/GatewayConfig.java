package ink.th.gateway.config;

import ink.th.gateway.filter.GatewayAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final GatewayAuthenticationFilter gatewayAuthenticationFilter;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r
                        .path("/v1/auth/**")
                        .filters(f -> f.filter(gatewayAuthenticationFilter))
                        .uri("lb://auth-service")
                )
                .route("user-service", r -> r
                        .path("/v1/users/**")
                        .filters(f -> f.filter(gatewayAuthenticationFilter))
                        .uri("lb://user-service")
                )
                .route("log-service", r -> r
                        .path("/v1/logs/**")
                        .filters(f -> f.filter(gatewayAuthenticationFilter))
                        .uri("lb://log-service")
                )
                .build();
    }
}
