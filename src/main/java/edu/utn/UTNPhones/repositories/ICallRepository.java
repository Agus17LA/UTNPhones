package edu.utn.UTNPhones.repositories;

import edu.utn.UTNPhones.domain.Call;
import edu.utn.UTNPhones.projections.MinutesOfCallNewYear2001;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICallRepository extends JpaRepository<Call,Integer> {

    @Query(value = "SELECT\n" +
            "TIME_FORMAT(sum(SEC_TO_TIME(ca.duration)),'%i:%s') AS 'MinutesOfNewYear2001'\n" +
            "FROM calls ca where date(ca.date_time) = date('2020-06-03');", nativeQuery = true)
    MinutesOfCallNewYear2001 getMinutesOfNewYear2001();
}
