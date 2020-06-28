package pl.hotelbooking.Hotel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.hotelbooking.Hotel.domain.BaseModel;
import pl.hotelbooking.Hotel.domain.BookingStatus;
import pl.hotelbooking.Hotel.domain.Reservation;
import pl.hotelbooking.Hotel.domain.Room;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BookingStatusDTO extends BaseModel {

    private Boolean reservationPaid;
    private BigDecimal totalAmountForReservation;

//    private Reservation reservation;
    private RoomDTO room;

    public BookingStatus toBookingStatus() {
        return BookingStatus.builder()
                .reservationPaid(reservationPaid)
                .totalAmountForReservation(totalAmountForReservation)
                .room(room.toRoom())
                .build();
    }
}
