package pl.hotelbooking.Hotel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import pl.hotelbooking.Hotel.domain.BaseModel;
import pl.hotelbooking.Hotel.domain.Role;
import pl.hotelbooking.Hotel.domain.User;

@Data
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
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

    public static UserDTO toUserDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .login(user.getLogin())
                .password(user.getPassword())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .city(user.getCity())
                .zipCode(user.getZipCode())
                .country(user.getCountry())
                .role(user.getRole())
                .build();
    }
}
