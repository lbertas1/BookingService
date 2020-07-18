package pl.hotelbooking.Hotel.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.hotelbooking.Hotel.domain.dto.UserDTO;
import pl.hotelbooking.Hotel.exceptions.EmailServiceException;
import pl.hotelbooking.Hotel.exceptions.UserServiceException;
import pl.hotelbooking.Hotel.services.EmailService;
import pl.hotelbooking.Hotel.services.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    ResponseEntity<UserDTO> getUser(@PathVariable Long userId) throws UserServiceException, EmailServiceException {
        // TYMCZASOWO
        EmailService.send("lbertas1@wp.pl", "emailFromHotelProject", "<h1> kupa gówna </h1>");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getUser(userId));
    }

    @PostMapping("/newUser")
    ResponseEntity<UserDTO> saveNewUser(@RequestBody UserDTO userDTO) throws UserServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveNewUser(userDTO));
    }

    // nie rozumiem, czemu put nie działa, zapisuje nowego, ale nie aktualizuje
    @PutMapping("/updateUser")
    ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) throws UserServiceException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body( userService.updateUser(userDTO));
    }

    @DeleteMapping("/removeUser/{userId}")
    ResponseEntity<Long> removeUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.removeUser(userId));
    }
}
