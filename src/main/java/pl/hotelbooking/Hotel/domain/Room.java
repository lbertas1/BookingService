package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.hotelbooking.Hotel.domain.dto.RoomDTO;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
public class Room extends BaseModel {

    private Integer roomNumber;
    private Integer roomCapacity;
    private String describe;
    private BigDecimal priceForNight;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BookingStatus> bookingStatuses;

    // dodałem, nie wiem czy stosowac te modele?
    // czy to ok format, jak wykorzystuje buildera i konstruktor?
    // w sumie to builder też pod spodem chyba leci na setterach
    public RoomDTO toRoomDto() {
        return new RoomDTO(
                getId(),
                getRoomNumber(),
                getRoomCapacity(),
                getDescribe(),
                getPriceForNight(),
                getBookingStatuses().stream().map(BookingStatus::toBookingStatusDto).collect(Collectors.toSet())
        );
    }
}
