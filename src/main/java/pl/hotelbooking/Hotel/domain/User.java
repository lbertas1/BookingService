package pl.hotelbooking.Hotel.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.hotelbooking.Hotel.domain.dto.UserDTO;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class User extends BaseModel {

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

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public UserDTO toUserDto() {
        return new UserDTO(
                getName(),
                getSurname(),
                getAge(),
                getLogin(),
                getPassword(),
                getEmail(),
                getPhone(),
                getAddress(),
                getCity(),
                getZipCode(),
                getCountry(),
                getRole()
        );
    }
}
