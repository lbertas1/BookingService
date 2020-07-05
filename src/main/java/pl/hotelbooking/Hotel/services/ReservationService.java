package pl.hotelbooking.Hotel.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hotelbooking.Hotel.domain.BookingStatus;
import pl.hotelbooking.Hotel.domain.Reservation;
import pl.hotelbooking.Hotel.domain.Room;
import pl.hotelbooking.Hotel.domain.dto.ReservationDTO;
import pl.hotelbooking.Hotel.domain.dto.RoomDTO;
import pl.hotelbooking.Hotel.domain.dto.UserDTO;
import pl.hotelbooking.Hotel.repository.ReservationRepository;
import pl.hotelbooking.Hotel.repository.RoomRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    private final RoomService roomService;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository, RoomService roomService) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.roomService = roomService;
    }

    @Transactional
    public void saveNewReservation(ReservationDTO reservation) {
        reservationRepository.save(reservation.toReservation());
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
                .map(ReservationDTO::toReservationDTO)
                .collect(Collectors.toList());

        return endingReservations.stream()
                .collect(Collectors.groupingBy(reservationDTO -> reservationDTO.getBookingStatusDTO().getReservationPaid()));
    }

    // DO SPRAWDZENIA MODYFIKOWANE
    public List<ReservationDTO> searchForUnpaidReservations() {
        return reservationRepository.findAllByStartOfBookingIsBeforeAndEndOfBookingIsAfter(LocalDate.now(), LocalDate.now()).stream()
                .filter(reservations -> !reservations.getBookingStatus().getReservationPaid())
                .map(ReservationDTO::toReservationDTO)
                .collect(Collectors.toList());
    }

    public void removeCompletedReservation() {
        reservationRepository.removeAllByEndOfBookingAfter(LocalDate.now());
    }

    // tworzenie nowego usera, jesli jeszcze nie ma go w bazie, ogarka w controllerze
    @Transactional
    public void addReservation(Long roomId, LocalDate from, LocalDate to, UserDTO userDTO) {
        List<RoomDTO> roomSought = roomService.getAllRooms().stream()
                .filter(room -> roomService.isRoomAvailableInGivenPeriod(roomId, from, to))
                .collect(Collectors.toList())
                .stream()
                .takeWhile(room -> room.getId().equals(roomId))
                .collect(Collectors.toList());

        Room room = roomRepository.findById(roomId).orElseThrow();

        if (!roomSought.get(0).getId().equals(roomId)) {
            // wyjebać wyjątek, że pokój nie jest dostępny?
        }

        Reservation reservation = Reservation.builder()
                .user(userDTO.toUser())
                .room(room)
                .startOfBooking(from)
                .endOfBooking(to)
                .bookingStatus(BookingStatus.builder().room(room).reservationPaid(false)
                        .totalAmountForReservation(calculatePriceForReservation(from, to, room.getPriceForNight())).build())
                .build();

        reservationRepository.save(reservation);
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream().map(ReservationDTO::toReservationDTO).collect(Collectors.toList());
    }

    // użyc w kontrolerze przy tworzeniu rezerwacji !!!
    public BigDecimal calculatePriceForReservation(LocalDate startOfBooking, LocalDate endOfBooking, BigDecimal priceForNight) {
        // sprawdzić tutaj czy to dobrze ogarnie dni
        Long days = ChronoUnit.DAYS.between(startOfBooking, endOfBooking);
        BigDecimal quantityOfDays = new BigDecimal(String.valueOf(days));
        return quantityOfDays.multiply(priceForNight);
    }
}
