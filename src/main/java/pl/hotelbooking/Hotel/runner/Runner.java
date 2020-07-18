package pl.hotelbooking.Hotel.runner;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.hotelbooking.Hotel.domain.dto.BookingStatusDTO;
import pl.hotelbooking.Hotel.domain.dto.ReservationDTO;
import pl.hotelbooking.Hotel.domain.dto.RoomDTO;
import pl.hotelbooking.Hotel.domain.dto.UserDTO;
import pl.hotelbooking.Hotel.repository.RoomRepository;
import pl.hotelbooking.Hotel.services.BookingStatusService;
import pl.hotelbooking.Hotel.services.ReservationService;
import pl.hotelbooking.Hotel.services.RoomService;
import pl.hotelbooking.Hotel.services.UserService;
import pl.hotelbooking.Hotel.services.mapper.EntityDtoMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    // czy w każdej metodzie muszę rzucać wyjątkiem??? w serwisie w sensie.

    // I JEDEN WGL JEST DO PRZETESTOWANIA CZY DZIAŁA, do poprawienia reservationController

    private final BookingStatusService bookingStatusService;
    private final ReservationService reservationService;
    private final RoomService roomService;
    private final UserService userService;
    private final RoomRepository roomRepository;
    private final EntityDtoMapper entityDtoMapper;

    @Override
    public void run(String... args) throws Exception {

        UserDTO user1 = UserDTO.builder()
                .id(1L)
                .name("Wiesiek")
                .surname("Brzoza")
                .age(57)
                .build();

        UserDTO user2 = UserDTO.builder()
                .id(2L)
                .name("Kazik")
                .surname("Bogdanka")
                .age(57)
                .build();

        UserDTO user3 = UserDTO.builder()
                .id(3L)
                .name("Hela")
                .surname("Września")
                .age(37)
                .build();

        // potworzyć te nowe obiekty i sprawdzić czy działa

        RoomDTO room1 = RoomDTO.builder()
                .roomNumber(1)
                .roomCapacity(500)
                .priceForNight(new BigDecimal("200"))
                .build();

        RoomDTO room2 = RoomDTO.builder()
                .roomNumber(2)
                .roomCapacity(2)
                .priceForNight(new BigDecimal("200"))
                .build();

        RoomDTO room3 = RoomDTO.builder()
                .roomNumber(3)
                .roomCapacity(2)
                .priceForNight(new BigDecimal("200"))
                .build();

        RoomDTO room4 = RoomDTO.builder()
                .roomNumber(4)
                .roomCapacity(2)
                .priceForNight(new BigDecimal("200"))
                .build();

        userService.saveNewUser(user1);
        userService.saveNewUser(user2);

        roomService.saveRoom(room1);
        roomService.saveRoom(room2);
        roomService.saveRoom(room3);
//        roomService.addRoom(RoomDTO.toRoomDTO(room4));
//

        BookingStatusDTO bookingStatusDTO1 = BookingStatusDTO.builder()
                .id(1L)
                .room(room1)
                .reservationPaid(false)
                .totalAmountForReservation(new BigDecimal("5000"))
                .build();

        BookingStatusDTO bookingStatusDTO2 = BookingStatusDTO.builder()
                .id(2L)
                .room(room2)
                .reservationPaid(false)
                .totalAmountForReservation(new BigDecimal("15000"))
                .build();

        BookingStatusDTO bookingStatusDTO3 = BookingStatusDTO.builder()
                .id(3L)
                .room(room3)
                .reservationPaid(false)
                .totalAmountForReservation(new BigDecimal("15000"))
                .build();

        ReservationDTO reservationDTO1 = ReservationDTO.builder()
                .id(1L)
                .room(room1)
                .user(user1)
                .bookingStatusDTO(bookingStatusDTO1)
                .startOfBooking(LocalDate.now().minusDays(10))
                .endOfBooking(LocalDate.now())
                .build();

        ReservationDTO reservationDTO2 = ReservationDTO.builder()
                .id(2L)
                .room(room2)
                .user(user2)
                .bookingStatusDTO(bookingStatusDTO2)
                .startOfBooking(LocalDate.now().minusDays(10))
                .endOfBooking(LocalDate.now())
                .build();

        ReservationDTO reservationDTO3 = ReservationDTO.builder()
                .id(3L)
                .room(room3)
                .user(user3)
                .bookingStatusDTO(bookingStatusDTO3)
                .startOfBooking(LocalDate.now().minusDays(10))
                .endOfBooking(LocalDate.now().minusDays(2))
                .build();

        reservationService.saveNewReservation(reservationDTO1);
        reservationService.saveNewReservation(reservationDTO2);
        reservationService.saveNewReservation(reservationDTO3);



//        System.out.println("reservationService.getAllReservations() = " + reservationService.getAllReservations());
//
//        System.out.println("===================================");
//
//        System.out.println("roomService.getAllRooms() = " + roomService.getAllRooms());
//
//        System.out.println("===================================");
//
//        System.out.println("roomService.getRoomById(1L) = " + roomService.getRoomById(1L));
//
//        System.out.println("===================================");
//
//        System.out.println("roomService.getAllBusyRooms() = " + roomService.getAllBusyRooms());
//
//        System.out.println("===================================");
//
//        System.out.println("roomService.getAllEmptyRooms() = " + roomService.getAllEmptyRooms());
//
//        System.out.println("===================================");
//
//        // działa
//        System.out.println("roomService.isRoomAvailableInGivenPeriod(1L, LocalDate.now(), LocalDate.now().plusDays(2)) = " + roomService.isRoomAvailableInGivenPeriod(roomDTO.getId(), LocalDate.now(), LocalDate.now().plusDays(2)));
//        System.out.println("roomService.isRoomAvailableInGivenPeriod(1L, LocalDate.now(), LocalDate.now().plusDays(2)) = " + roomService.isRoomAvailableInGivenPeriod(roomDTO.getId(), LocalDate.now().minusDays(4), LocalDate.now()));
//        System.out.println("roomService.isRoomAvailableInGivenPeriod(1L, LocalDate.now().minusDays(3), LocalDate.now().plusDays(7)) = " + roomService.isRoomAvailableInGivenPeriod(roomDTO.getId(), LocalDate.now().minusDays(3), LocalDate.now().plusDays(7)));
//        System.out.println("roomService.isRoomAvailableInGivenPeriod(1L, LocalDate.now().plusDays(7), LocalDate.now().plusDays(12)) = " + roomService.isRoomAvailableInGivenPeriod(roomDTO.getId(), LocalDate.now().plusDays(7), LocalDate.now().plusDays(12)));
//
//        System.out.println("===================================");
//
//        // działa
//        System.out.println("roomService.getAllEmptyRoomsInGivenPeriod(LocalDate.now(), LocalDate.now().plusDays(3)) === " + roomService.getAllEmptyRoomsInGivenPeriod(LocalDate.now(), LocalDate.now().plusDays(3)));
//        System.out.println("roomService.getAllEmptyRoomsInGivenPeriod(LocalDate.now(), LocalDate.now().plusDays(3)) === " + roomService.getAllEmptyRoomsInGivenPeriod(LocalDate.now().minusDays(5), LocalDate.now()));
//        System.out.println("roomService.getAllEmptyRoomsInGivenPeriod(LocalDate.now().plusDays(7), LocalDate.now().plusDays(12)) === " + roomService.getAllEmptyRoomsInGivenPeriod(LocalDate.now().plusDays(7), LocalDate.now().plusDays(12)));
//
//        System.out.println("===================================");
//
//        System.out.println("roomService.getAllRoomsForGivenCapacity(500) = " + roomService.getAllRoomsForGivenCapacity(500));
//        System.out.println("roomService.getAllRoomsForGivenCapacity(2) = " + roomService.getAllRoomsForGivenCapacity(2));

        // ============================= do tej pory działa, to roomService

//        System.out.println("bookingStatusService.getAllBookingStatuses() = " + bookingStatusService.getAllBookingStatuses());
//
//        List<BookingStatusDTO> bookingStatusDTOS = bookingStatusService.getAllBookingStatuses();
//
//        bookingStatusService.changePaymentStatus(bookingStatusDTOS.get(0).getId(), true);
//
//        System.out.println("bookingStatusService.getAllBookingStatuses() = " + bookingStatusService.getAllBookingStatuses());
//
//        System.out.println("reservationService.searchForEndingsReservations() = " + reservationService.searchForEndingsReservations());
        // ========================== ^^ działa

//        System.out.println("reservationService.searchForUnpaidReservations() = " + reservationService.searchForUnpaidReservations());
//
//        bookingStatusService.changePaymentStatus(roomDTO.getId(), true);
//
//        System.out.println("reservationService.searchForUnpaidReservations() = " + reservationService.searchForUnpaidReservations());

        // ^^ działaaaaa
    }
}
