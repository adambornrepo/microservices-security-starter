package ink.th.log.payload.response;

import java.io.Serializable;
import java.time.LocalDateTime;

public record LogResponse(
    String id,
    String message,
    String path,
    String service,
    String logLevel,
    LocalDateTime createdAt
) implements Serializable {
}
