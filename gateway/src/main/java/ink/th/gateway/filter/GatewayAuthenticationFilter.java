package ink.th.gateway.filter;

import ink.th.gateway.token.TokenService;
import ink.th.gateway.utils.RotateResolver;
import ink.th.gateway.utils.TokenProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GatewayAuthenticationFilter implements GatewayFilter {

    private final TokenProperties tokenProperties;
    private final RotateResolver rotate;
    private final TokenService tokenService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (rotate.isSecured.test(request)) {
            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith(tokenProperties.getPrefix()) || !tokenService.isTokenValid(authHeader.substring(7))) {
                var response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

                String errorMessage = "{\"message\": \"Invalid Token\"}";
                DataBufferFactory bufferFactory = response.bufferFactory();
                return response.writeWith(Mono.just(bufferFactory.wrap(errorMessage.getBytes())));
            }
        }
        return chain.filter(exchange);
    }

}
