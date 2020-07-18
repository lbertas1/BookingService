package pl.hotelbooking.Hotel.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hotelbooking.Hotel.domain.BaseModel;
import pl.hotelbooking.Hotel.domain.Reservation;
import pl.hotelbooking.Hotel.domain.Room;
import pl.hotelbooking.Hotel.domain.dto.ReservationDTO;
import pl.hotelbooking.Hotel.domain.dto.RoomDTO;
import pl.hotelbooking.Hotel.exceptions.RoomServiceException;
import pl.hotelbooking.Hotel.repository.ReservationRepository;
import pl.hotelbooking.Hotel.repository.RoomRepository;
import pl.hotelbooking.Hotel.services.mapper.EntityDtoMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    // SPRAWDZONE

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final EntityDtoMapper entityDtoMapper;

    @Transactional
    public RoomDTO saveRoom(RoomDTO roomDTO) throws RoomServiceException {
        if (Objects.isNull(roomDTO)) throw new RoomServiceException("Given room object is null");

        roomRepository.save(entityDtoMapper.toRoom(roomDTO));
        return roomDTO;
    }

    @Transactional
    public RoomDTO updateRoom(RoomDTO roomDTO) throws RoomServiceException {
        if (Objects.isNull(roomDTO)) throw new RoomServiceException("Given room object is null");

        roomRepository.save(entityDtoMapper.toRoom(roomDTO));
        return roomDTO;
    }

    public Long removeRoom(Long id) {
        roomRepository.deleteById(id);
        return id;
    }

    public List<RoomDTO> getAllRooms() {
        return roomRepository
                .findAll()
                .stream()
                .map(entityDtoMapper::toRoomDTO)
                .collect(Collectors.toList());
    }

    public RoomDTO getRoomById(Long id) throws RoomServiceException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RoomServiceException("Room was not found"));
        return entityDtoMapper.toRoomDTO(room);
    }

    public List<RoomDTO> getAllBusyRooms() {
        return reservationRepository
                .findAllByStartOfBookingBeforeAndEndOfBookingIsAfter(LocalDate.now(), LocalDate.now())
                .stream()
                .map(Reservation::getRoom)
                .map(entityDtoMapper::toRoomDTO)
                .collect(Collectors.toList());
    }

    // w przyszlosci ogarnac czy przez booking status np sie nie da zrobic tego krocej
    public List<RoomDTO> getAllEmptyRooms() {
        List<RoomDTO> allRooms = roomRepository
                .findAll()
                .stream()
                .map(entityDtoMapper::toRoomDTO)
                .collect(Collectors.toList());

        List<RoomDTO> allBusyRooms = getAllBusyRooms();

        return allRooms
                .stream()
                .filter(roomModel -> !allBusyRooms.contains(roomModel))
                .collect(Collectors.toList());
    }

    // działa, ale zmienić formę ściągania danych z bazy, żeby nie ściągać wszystkich rezerwacji bo to mało wydajne
    public boolean isRoomAvailableInGivenPeriod(Long id, LocalDate from, LocalDate to) {
        List<ReservationDTO> reservationsInGivenPeriod = reservationRepository
                .findAll()
                .stream()
                .map(entityDtoMapper::toReservationDTO)
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

         return getAllRooms()
                 .stream()
                .filter(roomDTO -> !roomsId.contains(roomDTO.getId()))
                .collect(Collectors.toList());
    }

    public List<RoomDTO> getAllRoomsForGivenCapacity(int capacity) {
        return roomRepository.findAllByRoomCapacity(capacity).stream().map(entityDtoMapper::toRoomDTO).collect(Collectors.toList());
    }

}
