package pl.hotelbooking.Hotel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ReservationServiceException extends Exception {
    public ReservationServiceException(String message) {
        super(message);
    }
}
