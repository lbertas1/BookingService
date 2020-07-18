package pl.hotelbooking.Hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.hotelbooking.Hotel.domain.Reservation;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Set<Reservation> findAllByStartOfBookingBeforeAndEndOfBookingIsAfter(LocalDate localDate, LocalDate localDate2);

    Set<Reservation> findAllByEndOfBookingIsBeforeAndBookingStatus_ReservationPaid(LocalDate end, boolean isPaid);

    Set<Reservation> findAllByStartOfBookingIsAfterAndEndOfBookingIsBeforeAndBookingStatus_ReservationPaid(LocalDate start, LocalDate end, boolean isPaid);

    Set<Reservation> findAllByEndOfBookingEquals(LocalDate date);

    Set<Reservation> removeAllByEndOfBookingBeforeAndBookingStatus_ReservationPaid(LocalDate today, Boolean reservationPaid);

    /*
    * //    to jest hql
    @Query(value = "select reservation from Reservation reservation "
            + "left join fetch reservation.room "
            + "where reservation.startOfBooking =:startOfBooking "
            + "and reservation.endOfBooking =:endOfBooking")
    Set<Reservation> findAllByStartOfBookingBeforeAndEndOfBookingIsAfter(LocalDate startOfBooking, LocalDate endOfBooking);*/
}
