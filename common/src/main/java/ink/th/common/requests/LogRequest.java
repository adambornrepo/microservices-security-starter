package ink.th.common.requests;

import ink.th.common.enums.LogLevel;

import java.io.Serializable;

public record LogRequest(
    String message,
    String path,
    String service,
    LogLevel logLevel
) implements Serializable {
}
