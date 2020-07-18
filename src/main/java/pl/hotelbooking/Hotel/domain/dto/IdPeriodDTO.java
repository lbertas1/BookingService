package pl.hotelbooking.Hotel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class IdPeriodDTO {

    private Long id;
    private LocalDate from;
    private LocalDate to;
}
