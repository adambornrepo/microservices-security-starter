package ink.th.user.client;

import feign.Response;
import feign.codec.ErrorDecoder;
import ink.th.user.exception.customs.ErrorDecodeException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        String message = null;
        try (InputStream body = response.body().asInputStream()) {
            message = IOUtils.toString(body, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new ErrorDecodeException(e.getMessage());
        }

        switch (response.status()) {
            case 400:
                return new IllegalArgumentException(message);
            case 404:
                return new IllegalArgumentException(message);
            case 500:
                return new IllegalArgumentException(message);
            default:
                return errorDecoder.decode(s, response);
        }
    }

}
