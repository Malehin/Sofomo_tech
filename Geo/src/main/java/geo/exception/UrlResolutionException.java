package geo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UrlResolutionException extends RuntimeException {
    public UrlResolutionException(String message) {
        super(message);
    }
}
