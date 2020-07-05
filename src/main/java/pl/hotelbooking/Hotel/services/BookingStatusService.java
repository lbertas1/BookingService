package pl.hotelbooking.Hotel.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hotelbooking.Hotel.domain.dto.BookingStatusDTO;
import pl.hotelbooking.Hotel.repository.BookingStatusRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingStatusService {

    private final BookingStatusRepository bookingStatusRepository;

    public BookingStatusService(BookingStatusRepository bookingStatusRepository) {
        this.bookingStatusRepository = bookingStatusRepository;
    }

    public BookingStatusDTO getBookingStatus(Long id) {
        return BookingStatusDTO.toBookingStatusDTO(bookingStatusRepository.findById(id).orElseThrow());
    }

    // tymczasowa metoda pomocnicza
    public List<BookingStatusDTO> getAllBookingStatuses() {
        return bookingStatusRepository.findAll().stream().map(BookingStatusDTO::toBookingStatusDTO).collect(Collectors.toList());
    }

    @Transactional
    public void changePaymentStatus(Long id, boolean paymentStatus) {
        // sprawdzenie uprawnien, sprawdzić czy to wystarczy, czy trzeba jeszcze zapisać
        bookingStatusRepository.getOne(id).setReservationPaid(paymentStatus);
    }

    // testowane, działa
}
