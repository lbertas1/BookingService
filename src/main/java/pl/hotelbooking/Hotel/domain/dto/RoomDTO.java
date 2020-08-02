package pl.hotelbooking.Hotel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import pl.hotelbooking.Hotel.domain.BaseModel;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class RoomDTO extends BaseModel {

    private Long id;
    private Integer roomNumber;
    private Integer roomCapacity;
    private String description;
    private BigDecimal priceForNight;

//    private Set<BookingStatusDTO> bookingStatuses;

}
