package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean reservationPaid;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "room_id")
    private Room room;
}
