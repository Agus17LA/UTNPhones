package edu.utn.UTNPhones.repositories;

import edu.utn.UTNPhones.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProvinceRepository extends JpaRepository<Province, Integer> {
}
