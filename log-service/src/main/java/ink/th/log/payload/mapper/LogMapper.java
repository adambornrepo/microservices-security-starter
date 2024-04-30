package ink.th.log.payload.mapper;


import ink.th.log.domain.entity.Log;
import ink.th.log.payload.request.LogRequest;
import ink.th.log.payload.response.LogResponse;
import org.springframework.stereotype.Component;

@Component
public class LogMapper {

    public LogResponse toLogResponse(Log log) {
        return new LogResponse(
                log.getId(),
                log.getMessage(),
                log.getPath(),
                log.getService(),
                log.getLogLevel().name(),
                log.getCreatedAt()
        );
    }

    public Log toLog(LogRequest request) {
        return Log.builder()
                .message(request.message())
                .path(request.path())
                .service(request.service())
                .logLevel(request.logLevel())
                .build();
    }
}
