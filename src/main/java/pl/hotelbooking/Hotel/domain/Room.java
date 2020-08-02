package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
public class Room extends BaseModel implements Serializable {

    private Integer roomNumber;
    private Integer roomCapacity;
    private String description;
    private BigDecimal priceForNight;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Set<BookingStatus> bookingStatuses;

}
