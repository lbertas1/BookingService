package pl.hotelbooking.Hotel.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.hotelbooking.Hotel.domain.dto.RoomDTO;
import pl.hotelbooking.Hotel.services.RoomService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    // nie wiem czy te mapowania na adres są ok
    // co z nullami, ogarniać to w serwisie i wywalać tam błąd jeśli jest null????
    // co z hateoas? dać tu coś jeszcze, jak np te linki właśnie, czy takie controllery są ok?

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<RoomDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @PostMapping("/addRoom")
    @ResponseStatus(HttpStatus.CREATED)
    void addRoom(@RequestBody RoomDTO room) {
        roomService.addRoom(room);
    }

    @GetMapping("/{id}")
    ResponseEntity<RoomDTO> getRoomById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PutMapping("/updateRoom")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void updateRoom(@RequestBody RoomDTO roomDTO) {
        roomService.updateRoom(roomDTO);
    }

    @DeleteMapping("/removeRoom/{id}")
    void deleteRoom(@PathVariable Long id) {
        roomService.removeRoom(id);
    }

    @GetMapping("/busyRooms")
    ResponseEntity<List<RoomDTO>> getAllBusyRooms() {
        return ResponseEntity.ok(roomService.getAllBusyRooms());
    }

    @GetMapping ("/emptyRooms")
    ResponseEntity<List<RoomDTO>> getAllEmptyRooms() {
        return ResponseEntity.ok(roomService.getAllEmptyRooms());
    }

    // jak to zaadresować ? to jest ok?
    // nie umiem tego przetestować w postmanie, nie wiem czy zły format daty daję, czy jak, błąd 500
    @GetMapping("/isAvailable/{id}")
    ResponseEntity<Boolean> isRoomAvailableInGivenPeriod(@PathVariable Long id, LocalDate from, LocalDate to) {
        return ResponseEntity.ok(roomService.isRoomAvailableInGivenPeriod(id, from, to));
    }

    // nie umiem przetestować w postmanie
    @GetMapping("/emptyRoomsInPeriod")
    ResponseEntity<List<RoomDTO>> getAllEmptyRoomsInGivenPeriod(LocalDate from, LocalDate to) {
        return ResponseEntity.ok(roomService.getAllEmptyRoomsInGivenPeriod(from, to));
    }

    @GetMapping ("/capacity/{capacity}")
    ResponseEntity<List<RoomDTO>> getAllRoomsForGivenCapacity(@PathVariable int capacity) {
        return ResponseEntity.ok(roomService.getAllRoomsForGivenCapacity(capacity));
    }

    // WSZYSTKO DZIAŁA W POSTMANIE, TYLKO TYCH DWÓCH NIE UMIEM PRZETESTOWAĆ !!!
}
