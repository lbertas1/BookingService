package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation extends BaseModel {


    private LocalDate startOfBooking;
    private LocalDate endOfBooking;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_guest_id")
    private User roomGuest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;
}
