package ink.th.log.controller;

import ink.th.log.payload.request.LogRequest;
import ink.th.log.payload.response.LogResponse;
import ink.th.log.service.LogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @PostMapping
    public ResponseEntity<LogResponse> createLog(@RequestBody @Valid LogRequest request) {
        return ResponseEntity.ok(logService.createLog(request));
    }

}
