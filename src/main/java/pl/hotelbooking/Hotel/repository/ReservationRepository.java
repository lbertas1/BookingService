package pl.hotelbooking.Hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.hotelbooking.Hotel.domain.Reservation;
import pl.hotelbooking.Hotel.domain.Room;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // czy to jest ok? czy zadziala?
    Set<Reservation> findAllByStartOfBookingBeforeAndEndOfBookingIsAfter(LocalDate localDate, LocalDate localDate2);

    Set<Reservation> findAllByEndOfBookingEquals(LocalDate date);

    Set<Reservation> findByRoom(Room room);

    Set<Reservation> findAllByEndOfBookingIsAfter(LocalDate today);

    Set<Reservation> removeAllByEndOfBookingAfter(LocalDate today);
}
