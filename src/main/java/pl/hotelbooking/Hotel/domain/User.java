package pl.hotelbooking.Hotel.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@NoArgsConstructor
//@Builder
// jak się będzie miało powiązanie między room, a user?
public class User extends BaseModel {

    private String name;
    private String surname;
    private String login;
    private String password;
    private String email;
    private String phone;

    private int age;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
