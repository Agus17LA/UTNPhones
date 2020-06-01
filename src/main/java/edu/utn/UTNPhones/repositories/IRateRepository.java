package edu.utn.UTNPhones.repositories;

import edu.utn.UTNPhones.domain.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRateRepository extends JpaRepository<Fare,Integer> {
}
