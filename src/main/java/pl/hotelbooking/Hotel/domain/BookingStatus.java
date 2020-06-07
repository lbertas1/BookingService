package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.hotelbooking.Hotel.domain.models.BookingStatusModel;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingStatus extends BaseModel {

    private Boolean reservationPaid;
    // date?

    // w przyszlosci jakies znizki dorobic
    private BigDecimal totalAmountForReservation;

    @OneToOne
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "room_id")
    private Room room;

    public BookingStatusModel toBookingStatusModel() {
        return new BookingStatusModel(
                getReservationPaid(),
                getTotalAmountForReservation(),
                getReservation().toReservationModel(),
                getRoom()
        );
    }
}
