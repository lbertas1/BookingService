package pl.hotelbooking.Hotel.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hotelbooking.Hotel.domain.BookingStatus;
import pl.hotelbooking.Hotel.domain.Reservation;
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
import java.util.Set;
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
    // nieskończona petla while?
    // ustawienie userów na nieaktywnych
    // przeniesienie do archiwum rezerwacji ?? ARCHIWUM??? GOOD IDEAD?

    // zwraca mapę opłaconych i nie opłaconych i tam ją obsłużyć na froncie
    private Map<Boolean, List<ReservationDTO>> searchForEndingsReservations() {
        List<ReservationDTO> endingReservations = reservationRepository
                .findAllByEndOfBookingEquals(LocalDate.now())
                .stream()
                .map(Reservation::toReservationDto)
                .collect(Collectors.toList());

        return endingReservations.stream()
                .collect(Collectors.groupingBy(reservationDTO -> reservationDTO.getBookingStatusDTO().getReservationPaid()));
    }

    private List<ReservationDTO> searchForUnpaidReservations(List<ReservationDTO> endingReservations) {
        return endingReservations.stream()
                .filter(reservationDTO -> !reservationDTO.getBookingStatusDTO().getReservationPaid())
                .collect(Collectors.toList());
    }

    public void removeCompletedReservation() {
        reservationRepository.removeAllByEndOfBookingAfter(LocalDate.now());
    }

    // tworzenie nowego usera, jesli jeszcze nie ma go w bazie, ogarka w controllerze
    @Transactional
    public void addReservation(Long roomId, LocalDate from, LocalDate to, UserDTO userDTO) {
        List<RoomDTO> roomSought = roomService.getAllRooms().stream()
                .filter(roomDTO -> roomService.isRoomAvailableInGivenPeriod(roomDTO.getId(), from, to))
                .collect(Collectors.toList())
                .stream()
                .takeWhile(roomDTO -> roomDTO.getId().equals(roomId))
                .collect(Collectors.toList());

        RoomDTO roomDTO = roomRepository.findById(roomId).orElseThrow().toRoomDto();

        Reservation reservation = Reservation.builder()
                .user(userDTO.toUser())
                .room(roomDTO.toRoom())
                .startOfBooking(from)
                .endOfBooking(to)
                .bookingStatus(BookingStatus.builder().room(roomDTO.toRoom()).reservationPaid(false)
                        .totalAmountForReservation(calculatePriceForReservation(from, to, roomDTO.getPriceForNight())).build())
                .build();

        reservationRepository.save(reservation);
    }

    public Set<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream().map(Reservation::toReservationDto).collect(Collectors.toSet());
    }

    // użyc w kontrolerze przy tworzeniu rejestracji !!!
    public BigDecimal calculatePriceForReservation(LocalDate startOfBooking, LocalDate endOfBooking, BigDecimal priceForNight) {
        // sprawdzić tutaj czy to dobrze ogarnie dni
        Long days = ChronoUnit.DAYS.between(startOfBooking, endOfBooking);
        BigDecimal quantityOfDays = new BigDecimal(String.valueOf(days));
        return quantityOfDays.multiply(priceForNight);
    }
}
