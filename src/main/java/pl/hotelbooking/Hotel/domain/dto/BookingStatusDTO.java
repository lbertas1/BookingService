package pl.hotelbooking.Hotel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import pl.hotelbooking.Hotel.domain.BaseModel;
import pl.hotelbooking.Hotel.domain.BookingStatus;

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

    public BookingStatus toBookingStatus() {
        return BookingStatus.builder()
                .reservationPaid(reservationPaid)
                .totalAmountForReservation(totalAmountForReservation)
                .room(room.toRoom())
                .build();
    }

    public static BookingStatusDTO toBookingStatusDTO(BookingStatus bookingStatus) {
        RoomDTO roomDTO = RoomDTO.builder().build();

        return BookingStatusDTO.builder()
                .id(bookingStatus.getId())
                .reservationPaid(bookingStatus.getReservationPaid())
                .totalAmountForReservation(bookingStatus.getTotalAmountForReservation())
                .room(RoomDTO.toRoomDTO(bookingStatus.getRoom()))
                .build();
    }
}
