package edu.utn.UTNPhones.repositories;

import edu.utn.UTNPhones.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityRepository extends JpaRepository<City, Integer> {
}
