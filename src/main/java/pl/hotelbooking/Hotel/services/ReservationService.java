package pl.hotelbooking.Hotel.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hotelbooking.Hotel.domain.dto.ReservationDTO;
import pl.hotelbooking.Hotel.exceptions.ReservationServiceException;
import pl.hotelbooking.Hotel.repository.ReservationRepository;
import pl.hotelbooking.Hotel.services.mapper.EntityDtoMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final EntityDtoMapper entityDtoMapper;

    @Transactional
    public ReservationDTO saveNewReservation(ReservationDTO reservationDTO) throws ReservationServiceException {
        if (Objects.isNull(reservationDTO)) throw new ReservationServiceException("Given reservation object is null");

        reservationRepository.save(entityDtoMapper.toReservation(reservationDTO));
        return reservationDTO;
    }

    // wysłanie informacji na front o kończących się rezerwacjach, endingReservations
    // wykorzystać info z wielowatkowosci i moze sleepa z timeUnit z wyliczaniem czasu od aktywacji metody do 00:00 np
    // ustawienie userów na nieaktywnych
    // przeniesienie do archiwum rezerwacji ?? ARCHIWUM??? GOOD IDEAD?

    // zwraca mapę opłaconych i nie opłaconych i tam ją obsłużyć na froncie, ogarnięcie tego z tym pomysłem na
    // timeUnitSleep-a ???????????
    // zmienić tu, podawać dni, które kończą się dziś i które kończą się jutro i pojutrze i czy są opłacone może, rozwinąć
    // tę logikę
    public Map<Boolean, List<ReservationDTO>> searchForEndingsReservations() {
        List<ReservationDTO> endingReservations = reservationRepository
                .findAllByEndOfBookingEquals(LocalDate.now())
                .stream()
                .map(entityDtoMapper::toReservationDTO)
                .collect(Collectors.toList());

        return endingReservations.stream()
                .collect(Collectors.groupingBy(reservationDTO -> reservationDTO.getBookingStatusDTO().getReservationPaid()));
    }

    public List<ReservationDTO> findAllUnpaidReservationToDate(LocalDate end) {
        return reservationRepository
                .findAllByEndOfBookingIsBeforeAndBookingStatus_ReservationPaid(end, false)
                .stream()
                .map(entityDtoMapper::toReservationDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationDTO> searchForUnpaidReservationsBetweenDates(LocalDate start, LocalDate end) {
        return reservationRepository
                .findAllByStartOfBookingIsAfterAndEndOfBookingIsBeforeAndBookingStatus_ReservationPaid(start, end, false)
                .stream()
                .map(entityDtoMapper::toReservationDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Set<ReservationDTO> removeCompletedReservation() {
        return reservationRepository
                .removeAllByEndOfBookingBeforeAndBookingStatus_ReservationPaid(LocalDate.now(), true)
                .stream()
                .map(entityDtoMapper::toReservationDTO)
                .collect(Collectors.toSet());
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream().map(entityDtoMapper::toReservationDTO).collect(Collectors.toList());
    }

    public BigDecimal calculatePriceForReservation(LocalDate startOfBooking, LocalDate endOfBooking, BigDecimal priceForNight) {
        Long days = ChronoUnit.DAYS.between(startOfBooking, endOfBooking);
        BigDecimal quantityOfDays = new BigDecimal(String.valueOf(days));
        return quantityOfDays.multiply(priceForNight);
    }
}
