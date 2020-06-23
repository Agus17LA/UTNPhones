package edu.utn.UTNPhones.repositories;

import edu.utn.UTNPhones.domain.City;
import edu.utn.UTNPhones.domain.Fare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFareRepository extends JpaRepository<Fare, Integer> {

    List<Fare> findAllByOriginCity(City city);

    Fare findByOriginCityAndDestinationCity(City cityO, City cityD);
}
