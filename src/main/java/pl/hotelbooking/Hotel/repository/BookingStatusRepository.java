package pl.hotelbooking.Hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.hotelbooking.Hotel.domain.BookingStatus;

// crud czy jpa? czym sie roznia?
@Repository
public interface BookingStatusRepository extends JpaRepository<BookingStatus, Long> {




}
