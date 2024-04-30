package ink.th.gateway.utils;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RotateResolver {

    private static final List<String> WHITE_LIST = List.of(
            "/v1/auth/login",
            "/v1/auth/signup",
            "/eureka/**"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> WHITE_LIST
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
