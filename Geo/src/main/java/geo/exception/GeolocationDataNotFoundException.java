package geo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GeolocationDataNotFoundException extends RuntimeException {
    public GeolocationDataNotFoundException(String message) {
        super(message);
    }
}
