package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class BookingStatus extends BaseModel implements Serializable {

    private Boolean reservationPaid;
    // date?

    // w przyszlosci jakies znizki dorobic
    private BigDecimal totalAmountForReservation;

//    @OneToOne
//    private Reservation reservation;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //@JoinColumn(name = "room_id")
    private Room room;

}
