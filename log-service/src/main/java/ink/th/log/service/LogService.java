package ink.th.log.service;

import ink.th.log.domain.entity.Log;
import ink.th.log.payload.mapper.LogMapper;
import ink.th.log.payload.request.LogRequest;
import ink.th.log.payload.response.LogResponse;
import ink.th.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;
    private final LogMapper logMapper;

    public LogResponse createLog(LogRequest request) {
        Log saved = logRepository.save(logMapper.toLog(request));
        return logMapper.toLogResponse(saved);
    }
}
