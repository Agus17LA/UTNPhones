package edu.utn.UTNPhones.services;

import edu.utn.UTNPhones.domain.Invoice;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.dtos.DatesDto;
import edu.utn.UTNPhones.exceptions.ValidationException;
import edu.utn.UTNPhones.repositories.IInvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class InvoiceService {
    @Autowired
    IInvoiceRepository invoiceRepository;

    public List<Invoice> getInvoicesBetweenDates(DatesDto dates, User loggedUser) throws ValidationException {
        if( dates.getSecondDate().isBefore(dates.getFirstDate())) throw new ValidationException("The second date must be greater than the first");
        return invoiceRepository.getInvoicesBetweenDates(dates.getFirstDate(),dates.getSecondDate(),loggedUser.getId());
    }

    public List getInvoices(Optional<String> numberLine) {
        return numberLine.isPresent() ? invoiceRepository.findAllByNumberLine(numberLine.get()).orElseThrow() : invoiceRepository.findAll();
    }
}
