package ink.th.log.payload.request;

import ink.th.log.domain.enums.LogLevel;

import java.io.Serializable;

public record LogRequest(
    String message,
    String path,
    String service,
    LogLevel logLevel
) implements Serializable {
}
