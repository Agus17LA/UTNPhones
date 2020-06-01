package edu.utn.UTNPhones.repositories;

import edu.utn.UTNPhones.domain.Fare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFareRepository extends JpaRepository<Fare, Integer> {

}
