package pl.hotelbooking.Hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
