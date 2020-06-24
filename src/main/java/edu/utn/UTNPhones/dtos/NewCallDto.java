package edu.utn.UTNPhones.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCallDto {
    private String originNumber;
    private String destinationNumber;
    private Integer duration;
    private LocalDateTime dateTime;

    public boolean verifyNullValues() { return Stream.of(originNumber,destinationNumber,duration,dateTime).anyMatch(x->x == null);  }
}
