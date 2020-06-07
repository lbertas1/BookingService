package pl.hotelbooking.Hotel.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.hotelbooking.Hotel.domain.BaseModel;
import pl.hotelbooking.Hotel.domain.BookingStatus;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
public class RoomModel extends BaseModel {

    private Integer roomNumber;
    private Integer roomCapacity;
    private String describe;
    private BigDecimal priceForNight;

    private Set<BookingStatus> bookingStatuses;
}
