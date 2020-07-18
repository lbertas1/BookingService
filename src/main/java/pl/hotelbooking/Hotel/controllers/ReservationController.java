package pl.hotelbooking.Hotel.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.hotelbooking.Hotel.domain.dto.BookingStatusDTO;
import pl.hotelbooking.Hotel.domain.dto.ReservationDTO;
import pl.hotelbooking.Hotel.exceptions.ReservationServiceException;
import pl.hotelbooking.Hotel.services.ReservationService;
import pl.hotelbooking.Hotel.services.RoomService;
import pl.hotelbooking.Hotel.services.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final RoomService roomService;
    private final UserService userService;

    @GetMapping
    ResponseEntity<Map<Boolean, List<ReservationDTO>>> searchForEndingReservations() {
        return ResponseEntity.ok(reservationService.searchForEndingsReservations());
    }

    @GetMapping("/unpaidReservationsBetween/{start}/{end}")
    ResponseEntity<List<ReservationDTO>> searchForUnpaidReservations(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        return ResponseEntity.ok(reservationService.searchForUnpaidReservationsBetweenDates(start, end));
    }

    // dodać pokazywanie pokoi do wyboru, żeby można było zaznaczyć pokój checkboxem i w ten sposób wybrać pokój do przesłania id
    // zrobić to na frontendzie, czy tutaj?
    @PostMapping("/createNewReservation")
    ResponseEntity<ReservationDTO> addReservation(@RequestBody ReservationDTO reservationDTO) throws ReservationServiceException {
        BigDecimal priceForReservation = reservationService.calculatePriceForReservation(reservationDTO.getStartOfBooking(),
                reservationDTO.getEndOfBooking(), reservationDTO.getRoom().getPriceForNight());

        reservationDTO.setBookingStatusDTO(BookingStatusDTO.builder().reservationPaid(false)
                .totalAmountForReservation(priceForReservation).room(reservationDTO.getRoom()).build());


        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.saveNewReservation(reservationDTO));
    }

    @GetMapping("/unpaidReservationToDate/{date}")
    ResponseEntity<List<ReservationDTO>> getAllUnpaidReservationsTo(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(reservationService.findAllUnpaidReservationToDate(date));
    }

    @DeleteMapping("/removeCompletedReservations")
    ResponseEntity<Set<ReservationDTO>> removeCompletedReservations() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(reservationService.removeCompletedReservation());
    }

    @GetMapping("/showAllReservations")
    ResponseEntity<List<ReservationDTO>> showAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }
}
