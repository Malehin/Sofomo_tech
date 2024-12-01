package geo.exception;

public class EmptyApiResponseException extends RuntimeException {
    public EmptyApiResponseException(String message) {
        super(message);
    }
}
