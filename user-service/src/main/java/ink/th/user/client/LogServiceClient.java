package ink.th.user.client;

import ink.th.common.requests.LogRequest;
import ink.th.common.responses.LogResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "log-service", path = "/v1/logs")
public interface LogServiceClient {

    @PostMapping
    @CircuitBreaker(name = "log-service-cb", fallbackMethod = "createLogFallback")
    ResponseEntity<LogResponse> createLog(@RequestBody @Valid LogRequest request);

}
