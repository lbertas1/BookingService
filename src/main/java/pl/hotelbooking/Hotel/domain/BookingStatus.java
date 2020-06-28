package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.hotelbooking.Hotel.domain.dto.BookingStatusDTO;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class BookingStatus extends BaseModel {

    private Boolean reservationPaid;
    // date?

    // w przyszlosci jakies znizki dorobic
    private BigDecimal totalAmountForReservation;

//    @OneToOne
//    private Reservation reservation;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "room_id")
    private Room room;

    public BookingStatusDTO toBookingStatusDto() {
        return new BookingStatusDTO(
                getReservationPaid(),
                getTotalAmountForReservation(),
//                getReservation(),
                getRoom().toRoomDto()
        );
    }
}
