package pl.hotelbooking.Hotel.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.hotelbooking.Hotel.domain.dto.IdPeriodDTO;
import pl.hotelbooking.Hotel.domain.dto.RoomDTO;
import pl.hotelbooking.Hotel.exceptions.RoomServiceException;
import pl.hotelbooking.Hotel.services.RoomService;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<RoomDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @PostMapping("/addRoom")
    ResponseEntity<RoomDTO> addRoom(@RequestBody RoomDTO room) throws RoomServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.saveRoom(room));
    }

    @GetMapping("/{id}")
    ResponseEntity<RoomDTO> getRoomById(@PathVariable("id") Long id) throws RoomServiceException {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PutMapping("/updateRoom")
    ResponseEntity<RoomDTO> updateRoom(@RequestBody RoomDTO roomDTO) throws RoomServiceException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(roomService.updateRoom(roomDTO));
    }

    @DeleteMapping("/removeRoom/{id}")
    ResponseEntity<Long> deleteRoom(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(roomService.removeRoom(id));
    }

    @GetMapping("/busyRooms")
    ResponseEntity<List<RoomDTO>> getAllBusyRooms() {
        return ResponseEntity.ok(roomService.getAllBusyRooms());
    }

    @GetMapping ("/emptyRooms")
    ResponseEntity<List<RoomDTO>> getAllEmptyRooms() {
        return ResponseEntity.ok(roomService.getAllEmptyRooms());
    }

    @PostMapping("/isAvailable")
    ResponseEntity<Boolean> isRoomAvailableInGivenPeriod(@RequestBody IdPeriodDTO idPeriodDTO) {
        return ResponseEntity.ok(roomService.isRoomAvailableInGivenPeriod(idPeriodDTO.getId(), idPeriodDTO.getFrom(), idPeriodDTO.getTo()));
    }

    @PostMapping("/emptyRoomsInPeriod")
    ResponseEntity<List<RoomDTO>> getAllEmptyRoomsInGivenPeriod(@RequestBody IdPeriodDTO idPeriodDTO) {
        return ResponseEntity.ok(roomService.getAllEmptyRoomsInGivenPeriod(idPeriodDTO.getFrom(), idPeriodDTO.getTo()));
    }

    @PostMapping ("/capacity/{capacity}")
    ResponseEntity<List<RoomDTO>> getAllRoomsForGivenCapacity(@PathVariable int capacity) {
        return ResponseEntity.ok(roomService.getAllRoomsForGivenCapacity(capacity));
    }
}
