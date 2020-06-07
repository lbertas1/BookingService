package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.hotelbooking.Hotel.domain.models.ReservationModel;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation extends BaseModel {

    private LocalDate startOfBooking;
    private LocalDate endOfBooking;

    // tu zmieniłem na set i manyToOne na ManyToMany
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_guest_id")
    private Set<User> users;

    // czy jedna rezerwacja nie powinna miec wielu pokoi? tu chyba powinienen być set
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne
    private BookingStatus bookingStatus;

    public ReservationModel toReservationModel(){
        return new ReservationModel(
                getStartOfBooking(),
                getEndOfBooking(),
                getUsers().stream().map(User::toUserModel).collect(Collectors.toSet()),
                getRoom().toRoomModel(),
                getBookingStatus().toBookingStatusModel()
        );
    }

}
