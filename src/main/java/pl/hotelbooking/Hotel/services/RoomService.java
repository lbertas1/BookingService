package pl.hotelbooking.Hotel.services;

import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hotelbooking.Hotel.domain.BaseModel;
import pl.hotelbooking.Hotel.domain.BookingStatus;
import pl.hotelbooking.Hotel.domain.Reservation;
import pl.hotelbooking.Hotel.domain.Room;
import pl.hotelbooking.Hotel.domain.dto.RoomDTO;
import pl.hotelbooking.Hotel.domain.dto.UserDTO;
import pl.hotelbooking.Hotel.repository.ReservationRepository;
import pl.hotelbooking.Hotel.repository.RoomRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoomService {

    // WSZYSTKO DO SPRAWDZENIA!!! CZY BĘDZIE DOBRZE DZIAŁAĆ

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
                .map(Room::toRoomDto)
                .collect(Collectors.toList());
    }

    public List<RoomDTO> getAllBusyRooms() {
        return reservationRepository.findAllByStartOfBookingBeforeAndEndOfBookingIsAfter(LocalDate.now(), LocalDate.now()).stream()
                .map(Reservation::getRoom)
                .map(Room::toRoomDto)
                .collect(Collectors.toList());
    }

    // w przyszlosci ogarnac czy przez booking status np sie nie da zrobic tego krocej
    public List<RoomDTO> getAllEmptyRooms() {
        List<RoomDTO> allRooms = roomRepository.findAll().stream()
                .map(Room::toRoomDto)
                .collect(Collectors.toList());

        List<RoomDTO> allBusyRooms = getAllBusyRooms();

        return allRooms.stream()
                .filter(roomModel -> !allBusyRooms.contains(roomModel))
                .collect(Collectors.toList());
    }

    // przetestowac
    public boolean isRoomAvailableInGivenPeriod(Long roomId, LocalDate from, LocalDate to) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        Set<Reservation> reservations = reservationRepository.findByRoom(room);
        List<Reservation> reservationsInGivenPeriod = reservations.stream()
                .filter(reservation -> reservation.getStartOfBooking().isBefore(from) || reservation.getEndOfBooking().isAfter(from) ||
                        reservation.getStartOfBooking().isBefore(to) || reservation.getEndOfBooking().isAfter(to))
                .collect(Collectors.toList());

        return reservationsInGivenPeriod.isEmpty();
    }

    // przetestować
    public Set<RoomDTO> getAllEmptyRoomsInGivenPeriod(LocalDate from, LocalDate to) {
        List<Reservation> allReservations = reservationRepository.findAll();
        Set<Long> roomsId = allReservations.stream()
                .filter(reservation -> reservation.getStartOfBooking().isBefore(from) || reservation.getEndOfBooking().isAfter(from) ||
                        reservation.getStartOfBooking().isBefore(to) || reservation.getEndOfBooking().isAfter(to))
                .map(Reservation::getRoom)
                .map(BaseModel::getId)
                .collect(Collectors.toSet());

         return getAllRooms().stream()
                .filter(roomDTO -> !roomsId.contains(roomDTO.getId()))
                .collect(Collectors.toSet());
    }

    public Set<RoomDTO> getAllRoomsForGivenCapacity(int capacity) {
        return roomRepository.findAllByRoomCapacity(capacity).stream().map(Room::toRoomDto).collect(Collectors.toSet());
    }

}
