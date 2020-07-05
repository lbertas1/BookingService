package pl.hotelbooking.Hotel.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hotelbooking.Hotel.domain.BaseModel;
import pl.hotelbooking.Hotel.domain.Reservation;
import pl.hotelbooking.Hotel.domain.Room;
import pl.hotelbooking.Hotel.domain.dto.ReservationDTO;
import pl.hotelbooking.Hotel.domain.dto.RoomDTO;
import pl.hotelbooking.Hotel.repository.ReservationRepository;
import pl.hotelbooking.Hotel.repository.RoomRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoomService {

    // SPRAWDZONE

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public RoomService(RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public void addRoom(RoomDTO room) {
        roomRepository.save(room.toRoom());
    }

    @Transactional
    public void updateRoom(RoomDTO roomDTO) {
        roomRepository.save(roomDTO.toRoom());
    }

    public void removeRoom(Long id) {
        roomRepository.deleteById(id);
    }

    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomDTO::toRoomDTO)
                .collect(Collectors.toList());
    }

    public RoomDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow();
        return RoomDTO.toRoomDTO(room);
    }

    public List<RoomDTO> getAllBusyRooms() {
        return reservationRepository.findAllByStartOfBookingBeforeAndEndOfBookingIsAfter(LocalDate.now(), LocalDate.now()).stream()
                .map(Reservation::getRoom)
                .map(RoomDTO::toRoomDTO)
                .collect(Collectors.toList());
    }

    // w przyszlosci ogarnac czy przez booking status np sie nie da zrobic tego krocej
    public List<RoomDTO> getAllEmptyRooms() {
        List<RoomDTO> allRooms = roomRepository.findAll().stream()
                .map(RoomDTO::toRoomDTO)
                .collect(Collectors.toList());

        List<RoomDTO> allBusyRooms = getAllBusyRooms();

        return allRooms.stream()
                .filter(roomModel -> !allBusyRooms.contains(roomModel))
                .collect(Collectors.toList());
    }

    // działa, ale zmienić formę ściągania danych z bazy, żeby nie ściągać wszystkich rezerwacji bo to mało wydajne
    public boolean isRoomAvailableInGivenPeriod(Long id, LocalDate from, LocalDate to) {
        // to nie działa
//        Room room = roomRepository.findById(id).orElseThrow();
//        Set<Reservation> reservations = reservationRepository.findByRoom(room);
//        List<Reservation> reservationsInGivenPeriod = reservations.stream()
//                .filter(reservation -> reservation.getStartOfBooking().isBefore(from) || reservation.getEndOfBooking().isAfter(from) ||
//                        reservation.getStartOfBooking().isBefore(to) || reservation.getEndOfBooking().isAfter(to))
//                .collect(Collectors.toList());

        List<ReservationDTO> reservationsInGivenPeriod = reservationRepository.findAll()
                .stream()
                .map(ReservationDTO::toReservationDTO)
                .filter(reservationDTO -> (reservationDTO.getStartOfBooking().isBefore(from) && reservationDTO.getEndOfBooking().isAfter(from)) ||
                        (reservationDTO.getStartOfBooking().isBefore(to) && reservationDTO.getEndOfBooking().isAfter(to)) ||
                        (reservationDTO.getStartOfBooking().isAfter(from) && reservationDTO.getEndOfBooking().isBefore(to)))
                .collect(Collectors.toList());

        return reservationsInGivenPeriod.isEmpty();
    }

    // działa
    public List<RoomDTO> getAllEmptyRoomsInGivenPeriod(LocalDate from, LocalDate to) {
        List<Reservation> allReservations = reservationRepository.findAll();
        Set<Long> roomsId = allReservations.stream()
                .filter(reservationDTO -> (reservationDTO.getStartOfBooking().isBefore(from) && reservationDTO.getEndOfBooking().isAfter(from)) ||
                        (reservationDTO.getStartOfBooking().isBefore(to) && reservationDTO.getEndOfBooking().isAfter(to)) ||
                        (reservationDTO.getStartOfBooking().isAfter(from) && reservationDTO.getEndOfBooking().isBefore(to)))
                .map(Reservation::getRoom)
                .map(BaseModel::getId)
                .collect(Collectors.toSet());

         return getAllRooms().stream()
                .filter(roomDTO -> !roomsId.contains(roomDTO.getId()))
                .collect(Collectors.toList());
    }

    public List<RoomDTO> getAllRoomsForGivenCapacity(int capacity) {
        return roomRepository.findAllByRoomCapacity(capacity).stream().map(RoomDTO::toRoomDTO).collect(Collectors.toList());
    }

}
