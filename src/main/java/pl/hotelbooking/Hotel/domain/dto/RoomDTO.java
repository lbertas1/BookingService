package pl.hotelbooking.Hotel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.hotelbooking.Hotel.domain.BaseModel;
import pl.hotelbooking.Hotel.domain.Room;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class RoomDTO extends BaseModel {

    private Integer roomNumber;
    private Integer roomCapacity;
    private String describe;
    private BigDecimal priceForNight;

    private Set<BookingStatusDTO> bookingStatuses;

    public Room toRoom() {
        return Room.builder()
                .roomNumber(roomNumber)
                .roomCapacity(roomCapacity)
                .describe(describe)
                .priceForNight(priceForNight)
                .bookingStatuses(bookingStatuses.stream().map(BookingStatusDTO::toBookingStatus).collect(Collectors.toSet()))
                .build();
    }
}
