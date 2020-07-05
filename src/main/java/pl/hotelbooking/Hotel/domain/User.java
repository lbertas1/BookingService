package pl.hotelbooking.Hotel.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
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

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;
}
