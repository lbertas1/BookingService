package pl.hotelbooking.Hotel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import pl.hotelbooking.Hotel.domain.Reservation;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
// powinno byc powiazanie do modeli czy do oryginału?
public class ReservationDTO {

    private Long id;
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

    public static ReservationDTO toReservationDTO(Reservation reservation) {
        UserDTO userDTO = UserDTO.builder().build();
        RoomDTO roomDTO = RoomDTO.builder().build();
        BookingStatusDTO bookingStatusDTO = BookingStatusDTO.builder().build();

        return ReservationDTO.builder()
                .id(reservation.getId())
                .startOfBooking(reservation.getStartOfBooking())
                .endOfBooking(reservation.getEndOfBooking())
                .user(UserDTO.toUserDto(reservation.getUser()))
                .room(RoomDTO.toRoomDTO(reservation.getRoom()))
                .bookingStatusDTO(bookingStatusDTO.toBookingStatusDTO(reservation.getBookingStatus()))
                .build();
    }
}
