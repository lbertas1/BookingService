package pl.hotelbooking.Hotel.runner;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.hotelbooking.Hotel.domain.Room;
import pl.hotelbooking.Hotel.domain.User;
import pl.hotelbooking.Hotel.domain.dto.RoomDTO;
import pl.hotelbooking.Hotel.domain.dto.UserDTO;
import pl.hotelbooking.Hotel.repository.RoomRepository;
import pl.hotelbooking.Hotel.services.BookingStatusService;
import pl.hotelbooking.Hotel.services.ReservationService;
import pl.hotelbooking.Hotel.services.RoomService;
import pl.hotelbooking.Hotel.services.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;

@Component
public class Runner implements CommandLineRunner {

    // pytania do Andrzeja. W klasach dto powinno być powiązanie do innych klas dto czy do oryginałów? w sensie np w bookingStatus mam
    // reservation to powinno być do reservationDto czy do oryginału?

    // sposób na przechodzenie do klasDTO, może być metoda static, czy tworzyć za każdym razem instancję klasyDTO, żeby się od niej odwołać?

    // jeśli w kończących się dziś rezerwacjach są nieopłacone to jakiś komunikat, czy błąd? metoda w reservationService, podejście do wyjątków

    // jak zmienić dto z powrotem na oryginał? żeby zapisać zmieniony obiekt w bazie danych, rezervationService, calculatePriceForReservation metoda

    // zapytać Andrzeja o wyjątki

    private final BookingStatusService bookingStatusService;
    private final ReservationService reservationService;
    private final RoomService roomService;
    private final UserService userService;
    private final RoomRepository roomRepository;

    public Runner(BookingStatusService bookingStatusService, ReservationService reservationService, RoomService roomService, UserService userService, RoomRepository roomRepository) {
        this.bookingStatusService = bookingStatusService;
        this.reservationService = reservationService;
        this.roomService = roomService;
        this.userService = userService;
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User user = User.builder()
                .id(1L)
                .name("Wiesiek")
                .surname("Brzoza")
                .age(57)
                .build();

        // potworzyć te nowe obiekty i sprawdzić czy działa

        Room room1 = Room.builder()
                .roomNumber(1)
                .roomCapacity(500)
                .priceForNight(new BigDecimal("200"))
                .bookingStatuses(new LinkedHashSet<>())
                .build();

        Room room2 = Room.builder()
                .roomNumber(2)
                .roomCapacity(2)
                .priceForNight(new BigDecimal("200"))
                .bookingStatuses(new LinkedHashSet<>())
                .build();

        Room room3 = Room.builder()
                .roomNumber(3)
                .roomCapacity(2)
                .priceForNight(new BigDecimal("200"))
                .bookingStatuses(new LinkedHashSet<>())
                .build();

        Room room4 = Room.builder()
                .roomNumber(4)
                .roomCapacity(2)
                .priceForNight(new BigDecimal("200"))
                .bookingStatuses(new LinkedHashSet<>())
                .build();

        roomService.addRoom(RoomDTO.toRoomDTO(room1));
        roomService.addRoom(RoomDTO.toRoomDTO(room2));
        roomService.addRoom(RoomDTO.toRoomDTO(room3));
        roomService.addRoom(RoomDTO.toRoomDTO(room4));

        RoomDTO roomDTO = RoomDTO.toRoomDTO(roomRepository.findAllByRoomCapacity(500).stream().reduce((room, room21) -> room).get());

        reservationService.addReservation(roomDTO.getId(), LocalDate.now().minusDays(2), LocalDate.now().plusDays(5), UserDTO.toUserDto(user));
        //reservationService.addReservation(roomDTO.getId(), LocalDate.now().minusDays(10), LocalDate.now(), UserDTO.toUserDto(user));

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
