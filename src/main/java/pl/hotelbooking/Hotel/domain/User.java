package pl.hotelbooking.Hotel.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.hotelbooking.Hotel.domain.enums.Role;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class User extends BaseModel {

    private String name;
    private String surname;
    private LocalDate birthDate;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String zipCode;
    private String country;
    private Boolean isNotLocked;
    @Enumerated(EnumType.STRING)
    private Role role;
}
