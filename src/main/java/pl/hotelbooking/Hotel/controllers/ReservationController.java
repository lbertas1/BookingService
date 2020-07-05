package pl.hotelbooking.Hotel.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.hotelbooking.Hotel.domain.dto.BookingStatusDTO;
import pl.hotelbooking.Hotel.domain.dto.ReservationDTO;
import pl.hotelbooking.Hotel.domain.dto.RoomDTO;
import pl.hotelbooking.Hotel.domain.dto.UserDTO;
import pl.hotelbooking.Hotel.repository.RoomRepository;
import pl.hotelbooking.Hotel.repository.UserRepository;
import pl.hotelbooking.Hotel.services.ReservationService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public ReservationController(ReservationService reservationService, RoomRepository roomRepository, UserRepository userRepository) {
        this.reservationService = reservationService;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    ResponseEntity<Map<Boolean, List<ReservationDTO>>> searchForEndingReservations() {
        return ResponseEntity.ok(reservationService.searchForEndingsReservations());
    }

    @GetMapping ("/unpaidReservations")
    ResponseEntity<List<ReservationDTO>> searchForUnpaidReservations() {
        return ResponseEntity.ok(reservationService.searchForUnpaidReservations());
    }

    // opakować wyjątkiem ze statusem
    // dodać pokazywanie pokoi do wyboru, żeby można było zaznaczyć pokój checkboxem i w ten sposób wybrać pokój do przesłania id
    // zrobić to na frontendzie, czy tutaj?
    @PostMapping("/createNewReservation")
    @ResponseStatus(HttpStatus.CREATED)
    void addReservation(@RequestBody ReservationDTO reservationDTO, Long roomId, Long userId) {
        // sprawdzenie czy user istniejem własny wyjątek
        UserDTO userDTO = UserDTO.toUserDto(userRepository.findById(userId).orElseThrow());
        reservationDTO.setUser(userDTO);
        // dodać własny wyjątek
        RoomDTO roomDTO = RoomDTO.toRoomDTO(roomRepository.findById(roomId).orElseThrow());
        reservationDTO.setRoom(roomDTO);

        BigDecimal priceForReservation = reservationService.calculatePriceForReservation(reservationDTO.getStartOfBooking(),
                reservationDTO.getEndOfBooking(), roomDTO.getPriceForNight());

        reservationDTO.setBookingStatusDTO(BookingStatusDTO.builder().reservationPaid(false)
                .totalAmountForReservation(priceForReservation).room(roomDTO).build());

        reservationService.saveNewReservation(reservationDTO);
    }

    @DeleteMapping ("/removeCompletedReservations")
    void removeCompletedReservations() {
        reservationService.removeCompletedReservation();
    }

    @GetMapping ("/showAllReservations")
    ResponseEntity<List<ReservationDTO>> showAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }
}
