package pl.hotelbooking.Hotel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class ReservationDTO {

    private Long id;
    private LocalDate startOfBooking;
    private LocalDate endOfBooking;

    private UserDTO user;

    private RoomDTO room;

    private BookingStatusDTO bookingStatusDTO;

}
