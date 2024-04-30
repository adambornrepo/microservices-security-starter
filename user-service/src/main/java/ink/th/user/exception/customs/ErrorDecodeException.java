package ink.th.user.exception.customs;

public class ErrorDecodeException extends RuntimeException {

    public ErrorDecodeException(String message) {
        super(message);
    }

    public ErrorDecodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
