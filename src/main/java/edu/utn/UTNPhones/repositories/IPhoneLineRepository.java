package edu.utn.UTNPhones.repositories;

import edu.utn.UTNPhones.domain.PhoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPhoneLineRepository extends JpaRepository<PhoneLine, Integer> {
}
