package pl.hotelbooking.Hotel.domain;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Role extends BaseModel {

    private String name;

}
