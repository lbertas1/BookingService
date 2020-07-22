package pl.hotelbooking.Hotel.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.hotelbooking.Hotel.domain.dto.BookingStatusDTO;
import pl.hotelbooking.Hotel.exceptions.BookingStatusServiceException;
import pl.hotelbooking.Hotel.services.BookingStatusService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookingStatus")
public class BookingStatusController {

    private final BookingStatusService bookingStatusService;

    @GetMapping("/get/{bookingStatusId}")
    ResponseEntity<BookingStatusDTO> getBookingStatus(@PathVariable Long bookingStatusId) throws BookingStatusServiceException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookingStatusService.getBookingStatus(bookingStatusId));
    }

    @GetMapping("/getAll")
    ResponseEntity<List<BookingStatusDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookingStatusService.getAllBookingStatuses());
    }

    @PatchMapping("/changePaymentStatus")
    ResponseEntity<BookingStatusDTO> changePaymentStatus(@RequestBody BookingStatusDTO bookingStatusDTO) throws BookingStatusServiceException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookingStatusService.changePaymentStatus(bookingStatusDTO));
    }
}
