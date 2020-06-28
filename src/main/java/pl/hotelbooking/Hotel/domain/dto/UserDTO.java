package pl.hotelbooking.Hotel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.hotelbooking.Hotel.domain.BaseModel;
import pl.hotelbooking.Hotel.domain.Role;
import pl.hotelbooking.Hotel.domain.User;

@Data
@AllArgsConstructor
public class UserDTO extends BaseModel {

    private String name;
    private String surname;
    private Integer age;
    private String login;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String zipCode;
    private String country;

    private Role role;

    public User toUser() {
        return User.builder()
                .name(name)
                .surname(surname)
                .age(age)
                .login(login)
                .password(password)
                .email(email)
                .phone(phone)
                .address(address)
                .city(city)
                .zipCode(zipCode)
                .country(country)
                .role(role)
                .build();
    }
}
