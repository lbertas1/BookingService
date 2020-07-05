package pl.hotelbooking.Hotel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import pl.hotelbooking.Hotel.domain.BaseModel;
import pl.hotelbooking.Hotel.domain.Room;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class RoomDTO extends BaseModel {

    private Long id;
    private Integer roomNumber;
    private Integer roomCapacity;
    private String describe;
    private BigDecimal priceForNight;

    private Set<BookingStatusDTO> bookingStatuses;

    public Room toRoom() {
        return Room.builder()
                .id(id)
                .roomNumber(roomNumber)
                .roomCapacity(roomCapacity)
                .describe(describe)
                .priceForNight(priceForNight)
                .bookingStatuses(bookingStatuses.stream().map(BookingStatusDTO::toBookingStatus).collect(Collectors.toSet()))
                .build();
    }

    public static RoomDTO toRoomDTO(Room room) {
        Set<BookingStatusDTO> bookingStatusDTOS = room.getBookingStatuses().stream()
                .map(BookingStatusDTO::toBookingStatusDTO)
                .collect(Collectors.toSet());

        return RoomDTO.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomCapacity(room.getRoomCapacity())
                .describe(room.getDescribe())
                .priceForNight(room.getPriceForNight())
                .bookingStatuses(bookingStatusDTOS)
                .build();
    }
}
