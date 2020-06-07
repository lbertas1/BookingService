package pl.hotelbooking.Hotel.services;

import org.springframework.stereotype.Service;
import pl.hotelbooking.Hotel.domain.BaseModel;
import pl.hotelbooking.Hotel.domain.Reservation;
import pl.hotelbooking.Hotel.domain.Room;
import pl.hotelbooking.Hotel.domain.models.RoomModel;
import pl.hotelbooking.Hotel.repository.ReservationRepository;
import pl.hotelbooking.Hotel.repository.RoomRepository;

import java.time.LocalDate;
import java.util.List;
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

    public List<RoomModel> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(Room::toRoomModel)
                .collect(Collectors.toList());
    }

    public List<RoomModel> getAllBusyRooms() {
        return reservationRepository.findAllByStartOfBookingBeforeAndEndOfBookingIsAfter(LocalDate.now()).stream()
                .map(Reservation::getRoom)
                .map(Room::toRoomModel)
                .collect(Collectors.toList());
    }

    // w przyszlosci ogarnac czy przez booking status np sie nie da zrobic tego krocej
    public List<RoomModel> getAllEmptyRooms() {
        List<RoomModel> allRooms = roomRepository.findAll().stream()
                .map(Room::toRoomModel)
                .collect(Collectors.toList());

        List<RoomModel> allBusyRooms = getAllBusyRooms();

        return allRooms.stream()
                .filter(roomModel -> !allBusyRooms.contains(roomModel))
                .collect(Collectors.toList());
    }

    // roomNumber lub id w zaleznosci od warstwy frontend
    // problem z wyciagnieciem id
    public boolean isRoomAvailable(Long id) {
        return getAllBusyRooms().stream()
                .map(BaseModel::getId)
                .collect(Collectors.toList())
                .stream()
                .anyMatch(aLong -> aLong.equals(id));
    }

    // pomyśleć nad kolejnymi funkcjami
}
