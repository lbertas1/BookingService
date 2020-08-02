package pl.hotelbooking.Hotel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsernameEmailPasswordDTO {
    private String username;
    private String email;
    private String password;
}
