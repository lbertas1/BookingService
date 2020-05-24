package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer roomNumber;
    private Integer roomCapacity;
    private Boolean isBusy = false;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private Set<User> guestSet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private Set<Reservation> reservation;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private Set<BookingStatus> bookingStatuses;

}
