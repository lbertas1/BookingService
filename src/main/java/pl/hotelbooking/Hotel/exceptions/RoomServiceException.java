package pl.hotelbooking.Hotel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RoomServiceException extends Exception {

    public RoomServiceException(String message) {
        super(message);
    }
}
