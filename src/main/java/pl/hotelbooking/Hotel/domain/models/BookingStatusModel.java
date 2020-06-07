package pl.hotelbooking.Hotel.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.hotelbooking.Hotel.domain.BaseModel;
import pl.hotelbooking.Hotel.domain.Room;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BookingStatusModel extends BaseModel {

    private Boolean reservationPaid;
    private BigDecimal totalAmountForReservation;

    private ReservationModel reservationModel;
    private Room room;
}
