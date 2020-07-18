package pl.hotelbooking.Hotel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import pl.hotelbooking.Hotel.domain.BaseModel;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class BookingStatusDTO extends BaseModel {

    private Long id;
    private Boolean reservationPaid;
    private BigDecimal totalAmountForReservation;

    //    private Reservation reservation;
    private RoomDTO room;
}
