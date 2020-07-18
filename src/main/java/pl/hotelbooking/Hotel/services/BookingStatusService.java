package pl.hotelbooking.Hotel.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hotelbooking.Hotel.domain.BookingStatus;
import pl.hotelbooking.Hotel.domain.dto.BookingStatusDTO;
import pl.hotelbooking.Hotel.exceptions.BookingStatusServiceException;
import pl.hotelbooking.Hotel.repository.BookingStatusRepository;
import pl.hotelbooking.Hotel.services.mapper.EntityDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingStatusService {

    private final BookingStatusRepository bookingStatusRepository;
    private final EntityDtoMapper entityDtoMapper;

    public BookingStatusDTO getBookingStatus(Long id) throws BookingStatusServiceException {
        return entityDtoMapper.toBookingStatusDTO(
                bookingStatusRepository
                        .findById(id)
                        .orElseThrow(() -> new BookingStatusServiceException("Booking status not found")));
    }

    public List<BookingStatusDTO> getAllBookingStatuses() {
        return bookingStatusRepository.findAll().stream().map(entityDtoMapper::toBookingStatusDTO).collect(Collectors.toList());
    }

    @Transactional
    public BookingStatusDTO changePaymentStatus(Long id, boolean paymentStatus) throws BookingStatusServiceException {
        // sprawdzenie uprawnien, sprawdzić czy to wystarczy, czy trzeba jeszcze zapisać
        BookingStatus bookingStatus = bookingStatusRepository
                .findById(id)
                .orElseThrow(() -> new BookingStatusServiceException("Booking status not found"));

        bookingStatus.setReservationPaid(paymentStatus);
        bookingStatusRepository.save(bookingStatus);
        return entityDtoMapper.toBookingStatusDTO(bookingStatus);
    }
}
