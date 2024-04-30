package ink.th.log.domain.entity;

import ink.th.log.domain.enums.LogLevel;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document(collection = "logs")
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @Id
    private String id;
    private String message;
    private String path;
    private String service;
    private LogLevel logLevel;
    private LocalDateTime createdAt;

}
