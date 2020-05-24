package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationNumber;

    private LocalDate startOfBooking;
    private LocalDate endOfBooking;

    @ManyToOne (fetch = FetchType.EAGER)
    //@JoinColumn(name = "room_id")
    private Room room;
}
