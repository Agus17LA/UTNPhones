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
@Table(name="cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="province_id", nullable = false)
    private Province province;

    @Column(name="city_name")
    private String cityName;

    @Column(name="city_prefix")
    private Integer cityPrefix;

    public boolean verifyNullValues(){
        return Stream.of(cityPrefix,cityName,province).anyMatch(x -> x == null);
    }

}
