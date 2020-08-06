package pl.hotelbooking.Hotel.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.hotelbooking.Hotel.domain.MyUserPrincipal;
import pl.hotelbooking.Hotel.domain.dto.UserDTO;
import pl.hotelbooking.Hotel.domain.dto.UsernameEmailPasswordDTO;
import pl.hotelbooking.Hotel.exceptions.EmailServiceException;
import pl.hotelbooking.Hotel.exceptions.UserServiceException;
import pl.hotelbooking.Hotel.security.JwtTokenProvider;
import pl.hotelbooking.Hotel.services.UserService;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;
import static pl.hotelbooking.Hotel.security.SecurityConstant.AUTHORIZATION;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UsernameEmailPasswordDTO usernamePasswordDTO) throws UserServiceException {
        authenticate(usernamePasswordDTO.getUsername(), usernamePasswordDTO.getPassword());
        UserDTO user = userService.findUserByUsername(usernamePasswordDTO.getUsername());
        MyUserPrincipal myUserPrincipal = new MyUserPrincipal(user);
        HttpHeaders jwtHeaders = getJwtHeader(myUserPrincipal);
        return new ResponseEntity<>(user, jwtHeaders, OK);
    }

    private HttpHeaders getJwtHeader(MyUserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        String token = tokenProvider.generateJwtToken(user);
        System.out.println(token);
        headers.add(AUTHORIZATION, token);
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) throws UserServiceException {
        UserDTO userDTOAfterSave = userService.register(userDTO);
        return new ResponseEntity<>(userDTOAfterSave, OK);
    }

    // zmienić na username zamiast id
    @GetMapping("/getUser/{userId}")
    ResponseEntity<UserDTO> getUser(@PathVariable Long userId) throws UserServiceException, EmailServiceException {
        // TYMCZASOWO
//        EmailService.send("lbertas1@wp.pl", "emailFromHotelProject", "<h1> kupa gówna </h1>");
        return ResponseEntity.status(ACCEPTED).body(userService.getUser(userId));
    }

    // tylko dla ROLE_ADMIN
    @PostMapping("/newUser")
    ResponseEntity<UserDTO> saveNewUser(@RequestBody UserDTO userDTO) throws UserServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveNewUser(userDTO));
    }

    @PutMapping("/updateUser")
    ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) throws UserServiceException {
        return ResponseEntity.status(ACCEPTED).body(userService.updateUser(userDTO));
    }

    // tak samo zmienić na username
    @DeleteMapping("/removeUser/{userId}")
    ResponseEntity<Long> removeUser(@PathVariable Long userId) {
        return ResponseEntity.status(ACCEPTED).body(userService.removeUser(userId));
    }

    @PostMapping("/changePassword")
    ResponseEntity<UserDTO> changePassword(@RequestBody UsernameEmailPasswordDTO usernameEmailPasswordDTO) throws UserServiceException {
        UserDTO userDTO = userService.changePassword(usernameEmailPasswordDTO.getUsername(), usernameEmailPasswordDTO.getEmail(), usernameEmailPasswordDTO.getPassword());
        return new ResponseEntity<>(userDTO, ACCEPTED);
    }

    @PostMapping("/changeRole")
    ResponseEntity<UserDTO> changeRoleOnAdmin(@RequestBody UsernameEmailPasswordDTO usernameEmailPasswordDTO) throws UserServiceException {
        UserDTO userDTO = userService.changeRoleOnAdmin(usernameEmailPasswordDTO.getUsername());
        return new ResponseEntity<>(userDTO, ACCEPTED);
    }
}
