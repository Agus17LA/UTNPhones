package edu.utn.UTNPhones.repositories;

import edu.utn.UTNPhones.domain.Call;
<<<<<<< HEAD
import edu.utn.UTNPhones.projections.CallOfUser;
import edu.utn.UTNPhones.projections.MinutesOfCallNewYear2001;
import edu.utn.UTNPhones.projections.TopTenDestinationsByUser;
=======
>>>>>>> parent of b03c9c7... Parcial Laboratorio 5
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICallRepository extends JpaRepository<Call,Integer> {
<<<<<<< HEAD

    @Query(value = "SELECT\n" +
            "TIME_FORMAT(sum(SEC_TO_TIME(ca.duration)),'%i:%s') AS 'MinutesOfNewYear2001'\n" +
            "FROM calls ca where date(ca.date_time) = date('2020-06-03');", nativeQuery = true)
    MinutesOfCallNewYear2001 getMinutesOfNewYear2001();

    @Query(value = "SELECT * FROM v_calls_of_user WHERE DniUserOrigin = ?",nativeQuery = true)
    List<CallOfUser> getCallsOfUser(String dni);

    @Query(value = "SELECT\n" +
            "FullNameUserOrigin,DniUserOrigin,OriginCall,OriginNumberLine,FullNameUserDest,DestinationNumberLine,DestinationCall,Invoice,Duration,CallDate,TotalPrice\n" +
            " FROM v_calls_of_user WHERE CallDate >= ?1 AND CallDate <= ?2 AND DniUserOrigin = ?3",nativeQuery = true)
    List<CallOfUser> getCallsByDates(String firstDate, String secondDate, String dni);


    @Query(value = "SELECT count(DestinationCall) as \"times\", DestinationCall \n" +
            "FROM v_calls_of_user \n" +
            "WHERE DniUserOrigin = ? \n" +
            "GROUP BY DestinationCall ORDER BY DestinationCall ASC LIMIT 10;", nativeQuery = true)
    List<TopTenDestinationsByUser> getTopTenDestinationsByUser(String idCard);
=======
>>>>>>> parent of b03c9c7... Parcial Laboratorio 5
}
