package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingStatus extends BaseModel {

    private Boolean reservationPaid;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "room_id")
    private Room room;
}
