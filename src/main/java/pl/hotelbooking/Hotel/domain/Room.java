package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.hotelbooking.Hotel.domain.models.RoomModel;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Room extends BaseModel {

    private Integer roomNumber;
    private Integer roomCapacity;
    private String describe;
    private BigDecimal priceForNight;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<BookingStatus> bookingStatuses;

    // dodałem, nie wiem czy stosowac te modele?
    // czy to ok format, jak wykorzystuje buildera i konstruktor?
    // w sumie to builder też pod spodem chyba leci na setterach
    public RoomModel toRoomModel() {
        return new RoomModel(
                getRoomNumber(),
                getRoomCapacity(),
                getDescribe(),
                getPriceForNight(),
                getBookingStatuses()
        );
    }
}
