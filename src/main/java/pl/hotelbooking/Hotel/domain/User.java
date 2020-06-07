package pl.hotelbooking.Hotel.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.hotelbooking.Hotel.domain.models.UserModel;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@NoArgsConstructor
@Builder
// jak się będzie miało powiązanie między room, a user?
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

    public UserModel toUserModel() {
        return new UserModel(
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
