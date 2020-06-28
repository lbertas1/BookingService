package pl.hotelbooking.Hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.hotelbooking.Hotel.domain.Room;

import java.util.Set;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Set<Room> findAllByRoomCapacity(int capacity);
}
