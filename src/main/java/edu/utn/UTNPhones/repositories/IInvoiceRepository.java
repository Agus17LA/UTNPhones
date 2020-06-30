package edu.utn.UTNPhones.repositories;

import edu.utn.UTNPhones.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query(value = "SELECT * FROM invoices i INNER JOIN phone_lines pl ON i.number_line = pl.number_line WHERE " +
            "(i.invoice_date BETWEEN ?1 AND ?2) AND pl.id = ?3",nativeQuery = true)
    List<Invoice> getInvoicesBetweenDates(String firstDate, String secondDate, Integer id);

    Optional<List<Invoice>> findAllByNumberLine(String numberLine);
}
