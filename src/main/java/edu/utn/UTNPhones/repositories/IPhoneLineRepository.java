package edu.utn.UTNPhones.repositories;

import edu.utn.UTNPhones.domain.PhoneLine;
import edu.utn.UTNPhones.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPhoneLineRepository extends JpaRepository<PhoneLine, Integer> {
    Optional<List<PhoneLine>> findByNumberLine(String numberLine);

    Optional<PhoneLine> getByNumberLine(String numberLine);

    List<PhoneLine> findAllByUser(User user);
}
