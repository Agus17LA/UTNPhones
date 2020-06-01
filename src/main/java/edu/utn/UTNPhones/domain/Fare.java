package edu.utn.UTNPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="fares")
public class Fare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="origin_city_id", nullable = false)
    private City originCity;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="destination_city_id", nullable = false)
    private City destinationCity;

    @Column(name="cost_per_minute")

    private Float costPerMinute;

    @Column(name="price_per_minute")

    private Float pricePerMinute;

    public boolean verifyNullValues(){
        return Stream.of(originCity,destinationCity,costPerMinute,pricePerMinute).anyMatch(x -> x == null);
    }

}
