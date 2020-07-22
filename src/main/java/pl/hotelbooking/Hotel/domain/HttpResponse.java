package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class HttpResponse {

    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String message;
}
