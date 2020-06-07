package pl.hotelbooking.Hotel.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.hotelbooking.Hotel.domain.BaseModel;
import pl.hotelbooking.Hotel.domain.Role;

@Data
@AllArgsConstructor
public class UserModel extends BaseModel {

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
}
