package pl.hotelbooking.Hotel.services;

import org.springframework.stereotype.Service;
import pl.hotelbooking.Hotel.domain.Reservation;
import pl.hotelbooking.Hotel.domain.models.BookingStatusModel;
import pl.hotelbooking.Hotel.domain.models.ReservationModel;
import pl.hotelbooking.Hotel.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void saveNewReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    // wysłanie informacji na front o kończących się rezerwacjach, endingReservations
    // nieskończona petla while?
    // ustawienie userów na nieaktywnych
    // przeniesienie do archiwum rezerwacji ?? ARCHIWUM??? GOOD IDEAD?
    // przed wyslaniem info na front sprawdzenie statusu, czy pokoj oplacony
    // zwrot informacji czy wszystkie rezerwacje sa juz oplacone, czy jakas nie jest
    private void searchForEndingsReservations() {
        while (true) {
            if (LocalTime.now().equals(LocalTime.MIDNIGHT)) {
                List<ReservationModel> endingReservations = reservationRepository.
                        findAllByEndOfBookingEquals(LocalDate.now())
                        .stream()
                        .map(Reservation::toReservationModel)
                        .collect(Collectors.toList());

                if (!searchForUnpaidReservations(endingReservations).isEmpty()) {
                    // wyswietlenie bledu ???
                }
            }
        }
    }

    private List<ReservationModel> searchForUnpaidReservations(List<ReservationModel> endingReservations) {
        return endingReservations.stream()
                .map(ReservationModel::getBookingStatusModel)
                .filter(bookingStatusModel -> !bookingStatusModel.getReservationPaid())
                .map(BookingStatusModel::getReservationModel)
                .collect(Collectors.toList());
    }
}
