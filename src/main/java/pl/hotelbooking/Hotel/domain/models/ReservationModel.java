package pl.hotelbooking.Hotel.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
// powinno byc powiazanie do modeli czy do oryginału?
public class ReservationModel {

    private LocalDate startOfBooking;
    private LocalDate endOfBooking;

    private Set<UserModel> users;

    private RoomModel room;

    private BookingStatusModel bookingStatusModel;
}
