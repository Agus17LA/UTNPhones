package edu.utn.UTNPhones.repositories;

import edu.utn.UTNPhones.domain.Call;
import edu.utn.UTNPhones.projections.CallOfUser;
import edu.utn.UTNPhones.projections.Calls;
import edu.utn.UTNPhones.projections.MinutesOfCallNewYear2001;
import edu.utn.UTNPhones.projections.TopTenDestinationsByUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICallRepository extends JpaRepository<Call,Integer> {

    @Query(value = "SELECT\n" +
            "TIME_FORMAT(sum(SEC_TO_TIME(ca.duration)),'%i:%s') AS 'MinutesOfNewYear2001'\n" +
            "FROM calls ca where date(ca.date_time) = date('2001-01-01');", nativeQuery = true)
    MinutesOfCallNewYear2001 getMinutesOfNewYear2001(); //Parcial laboratorio

    @Query(value = "SELECT * FROM v_calls_of_user WHERE DniUserOrigin = ?",nativeQuery = true)
    List<CallOfUser> getCallsOfUser(String dni);

    @Query(value = "SELECT " +
            "origin_number_line as 'OriginNumberLine'," +
            "origin_city_id as 'OriginCall'," +
            "destination_number_line as 'DestinationNumberLine'," +
            "destination_city_id as 'DestinationCall'," +
            "total_price as 'TotalPrice'," +
            "date_time as 'CallDate'," +
            " duration as 'Duration'" +
            " FROM Calls WHERE (date_time BETWEEN ?1 AND ?2) AND origin_number_line = ?3", nativeQuery = true)
    List<Calls> getCallsByDatesAndNumber(String firstDate, String secondDate, String numberLine);


    @Query(value = "SELECT \n" +
            "\tcount(destination_number_line) as 'times',\n" +
            "\tdestination_number_line as 'destinationCall' \n" +
            "FROM Calls c \n" +
            "INNER JOIN phone_lines pl ON c.origin_line_id = pl.id \n" +
            "WHERE user_id = ?1 group by destination_number_line ORDER BY destination_number_line ASC LIMIT 10;", nativeQuery = true)
    List<TopTenDestinationsByUser> getTopTenDestinationsByUser(Integer id);

}
