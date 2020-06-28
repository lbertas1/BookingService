package pl.hotelbooking.Hotel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.hotelbooking.Hotel.domain.BookingStatus;
import pl.hotelbooking.Hotel.domain.Reservation;
import pl.hotelbooking.Hotel.domain.Room;
import pl.hotelbooking.Hotel.domain.User;

import java.time.LocalDate;

@Data
@AllArgsConstructor
// powinno byc powiazanie do modeli czy do oryginału?
public class ReservationDTO {

    private LocalDate startOfBooking;
    private LocalDate endOfBooking;

    private UserDTO user;

    private RoomDTO room;

    private BookingStatusDTO bookingStatusDTO;

    public Reservation toReservation() {
        return Reservation.builder()
                .startOfBooking(startOfBooking)
                .endOfBooking(endOfBooking)
                .user(user.toUser())
                .room(room.toRoom())
                .bookingStatus(bookingStatusDTO.toBookingStatus())
                .build();
    }
}
