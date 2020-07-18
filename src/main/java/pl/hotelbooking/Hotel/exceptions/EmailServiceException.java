package pl.hotelbooking.Hotel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class EmailServiceException extends Exception {

    public EmailServiceException(String message) {
        super(message);
    }
}
