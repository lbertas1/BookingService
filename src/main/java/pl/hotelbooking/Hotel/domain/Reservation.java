package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.hotelbooking.Hotel.domain.dto.ReservationDTO;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Reservation extends BaseModel {

    private LocalDate startOfBooking;
    private LocalDate endOfBooking;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_guest_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne
    private BookingStatus bookingStatus;

    public ReservationDTO toReservationDto(){
        return new ReservationDTO(
                getStartOfBooking(),
                getEndOfBooking(),
                getUser().toUserDto(),
                getRoom().toRoomDto(),
                getBookingStatus().toBookingStatusDto()
        );
    }

}
