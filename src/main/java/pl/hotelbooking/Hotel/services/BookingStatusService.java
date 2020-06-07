package pl.hotelbooking.Hotel.services;

import org.springframework.stereotype.Service;
import pl.hotelbooking.Hotel.domain.models.BookingStatusModel;
import pl.hotelbooking.Hotel.repository.BookingStatusRepository;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Service
public class BookingStatusService {

    private final BookingStatusRepository bookingStatusRepository;

    public BookingStatusService(BookingStatusRepository bookingStatusRepository) {
        this.bookingStatusRepository = bookingStatusRepository;
    }

    public BigDecimal calculatePriceForReservation(Long id) {
        BookingStatusModel tmpBookingStatusModel = bookingStatusRepository.getOne(id).toBookingStatusModel();
        // sprawdzic, czy dobrze policzy, nie pamietam kolejnosci. albo math.abs
        Long days = ChronoUnit.DAYS.between(tmpBookingStatusModel.getReservation().getEndOfBooking(), tmpBookingStatusModel.getReservation().getStartOfBooking());
        BigDecimal quantityOfDays = new BigDecimal(String.valueOf(days));
        BigDecimal priceForNight = new BigDecimal(String.valueOf(tmpBookingStatusModel.getRoom().getPriceForNight()));
        BigDecimal totalAmount = quantityOfDays.multiply(priceForNight);
        tmpBookingStatusModel.setTotalAmountForReservation(totalAmount);
        return totalAmount;
    }

    public void changePaymentStatus(Long id, boolean paymentStatus) {
        // sprawdzenie uprawnien
        bookingStatusRepository.getOne(id).setReservationPaid(paymentStatus);
    }

}
