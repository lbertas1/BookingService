package pl.hotelbooking.Hotel.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hotelbooking.Hotel.domain.dto.BookingStatusDTO;
import pl.hotelbooking.Hotel.repository.BookingStatusRepository;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Service
public class BookingStatusService {

    private final BookingStatusRepository bookingStatusRepository;

    public BookingStatusService(BookingStatusRepository bookingStatusRepository) {
        this.bookingStatusRepository = bookingStatusRepository;
    }

    @Transactional
    public void changePaymentStatus(Long id, boolean paymentStatus) {
        // sprawdzenie uprawnien, sprawdzić czy to wystarczy, czy trzeba jeszcze zapisać
        bookingStatusRepository.getOne(id).setReservationPaid(paymentStatus);
    }

}
