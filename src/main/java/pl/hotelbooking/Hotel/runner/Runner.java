package pl.hotelbooking.Hotel.runner;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.hotelbooking.Hotel.domain.Room;
import pl.hotelbooking.Hotel.domain.User;
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

    // jeśli w kończących się dziś rezerwacjach są nieopłacone to jakiś komunikat, czy błąd? metoda w reservationService

    // jak zmienić dto z powrotem na oryginał? żeby zapisać zmieniony obiekt w bazie danych, rezervationService, calculatePriceForReservation metoda

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
                .roomCapacity(2)
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

        roomService.addRoom(room1.toRoomDto());
        roomService.addRoom(room2.toRoomDto());
        roomService.addRoom(room3.toRoomDto());
        roomService.addRoom(room4.toRoomDto());

        System.out.println("roomService.getAllRooms() = " + roomService.getAllRooms());

        System.out.println("===================================");

        System.out.println("reservationService.getAllReservations() = " + reservationService.getAllReservations());

        reservationService.addReservation(roomRepository.getOne(1L).getId(), LocalDate.now(), LocalDate.now().plusDays(5), user.toUserDto());

        System.out.println("reservationService.getAllReservations() = " + reservationService.getAllReservations());

        System.out.println("===================================");
    }
}
