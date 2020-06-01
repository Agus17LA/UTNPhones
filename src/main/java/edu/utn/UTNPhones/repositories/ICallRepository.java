package edu.utn.UTNPhones.repositories;

import edu.utn.UTNPhones.domain.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICallRepository extends JpaRepository<Call,Integer> {
}
