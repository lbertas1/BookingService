package pl.hotelbooking.Hotel.services.mapper;

import org.springframework.stereotype.Service;
import pl.hotelbooking.Hotel.domain.BookingStatus;
import pl.hotelbooking.Hotel.domain.Reservation;
import pl.hotelbooking.Hotel.domain.Room;
import pl.hotelbooking.Hotel.domain.User;
import pl.hotelbooking.Hotel.domain.dto.BookingStatusDTO;
import pl.hotelbooking.Hotel.domain.dto.ReservationDTO;
import pl.hotelbooking.Hotel.domain.dto.RoomDTO;
import pl.hotelbooking.Hotel.domain.dto.UserDTO;

@Service
public class EntityDtoMapper {

    public User toUser(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .birthDate(userDTO.getBirthDay())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .country(userDTO.getCountry())
                .zipCode(userDTO.getZipCode())
                .city(userDTO.getCity())
                .address(userDTO.getAddress())
                .phone(userDTO.getPhone())
                .email(userDTO.getEmail())
                .isNotLocked(userDTO.getIsNotLocked())
                .role(userDTO.getRole())
                .build();
    }

    public UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .birthDay(user.getBirthDate())
                .username(user.getUsername())
                .password(user.getPassword())
                .country(user.getCountry())
                .zipCode(user.getZipCode())
                .city(user.getCity())
                .address(user.getAddress())
                .phone(user.getPhone())
                .email(user.getEmail())
                .isNotLocked(user.getIsNotLocked())
                .role(user.getRole())
                .build();
    }

    public Room toRoom (RoomDTO roomDTO) {
        return Room.builder()
                .id(roomDTO.getId())
                .priceForNight(roomDTO.getPriceForNight())
                .description(roomDTO.getDescription())
                .roomCapacity(roomDTO.getRoomCapacity())
                .roomNumber(roomDTO.getRoomNumber())
                .build();

    }

    public RoomDTO toRoomDTO (Room room) {
        return RoomDTO.builder()
                .id(room.getId())
                .priceForNight(room.getPriceForNight())
                .description(room.getDescription())
                .roomCapacity(room.getRoomCapacity())
                .roomNumber(room.getRoomNumber())
                .build();
    }

    public Reservation toReservation(ReservationDTO reservationDTO) {
        return Reservation.builder()
                .id(reservationDTO.getId())
                .bookingStatus(toBookingStatus(reservationDTO.getBookingStatusDTO()))
                .room(toRoom(reservationDTO.getRoom()))
                .user(toUser(reservationDTO.getUser()))
                .startOfBooking(reservationDTO.getStartOfBooking())
                .endOfBooking(reservationDTO.getEndOfBooking())
                .build();
    }

    public ReservationDTO toReservationDTO (Reservation reservation) {
        return ReservationDTO.builder()
                .id(reservation.getId())
                .bookingStatusDTO(toBookingStatusDTO(reservation.getBookingStatus()))
                .room(toRoomDTO(reservation.getRoom()))
                .user(toUserDTO(reservation.getUser()))
                .startOfBooking(reservation.getStartOfBooking())
                .endOfBooking(reservation.getEndOfBooking())
                .build();
    }

    public BookingStatus toBookingStatus (BookingStatusDTO bookingStatusDTO) {
        return BookingStatus.builder()
                .id(bookingStatusDTO.getId())
                .room(toRoom(bookingStatusDTO.getRoom()))
                .totalAmountForReservation(bookingStatusDTO.getTotalAmountForReservation())
                .reservationPaid(bookingStatusDTO.getReservationPaid())
                .build();
    }

    public BookingStatusDTO toBookingStatusDTO (BookingStatus bookingStatus) {
        return BookingStatusDTO.builder()
                .id(bookingStatus.getId())
                .room(toRoomDTO(bookingStatus.getRoom()))
                .totalAmountForReservation(bookingStatus.getTotalAmountForReservation())
                .reservationPaid(bookingStatus.getReservationPaid())
                .build();
    }
}
