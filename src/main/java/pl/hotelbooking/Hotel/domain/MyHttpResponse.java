package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyHttpResponse {

    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String message;
}
