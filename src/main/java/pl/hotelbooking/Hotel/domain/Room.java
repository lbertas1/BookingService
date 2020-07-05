package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Set;

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

}
