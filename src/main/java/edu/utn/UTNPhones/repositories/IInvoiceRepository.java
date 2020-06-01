package edu.utn.UTNPhones.repositories;

import edu.utn.UTNPhones.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Integer> {
}
