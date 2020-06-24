package edu.utn.UTNPhones.controllers.domain;

import edu.utn.UTNPhones.domain.Invoice;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.dtos.DatesDto;
import edu.utn.UTNPhones.exceptions.ValidationException;
import edu.utn.UTNPhones.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    public List<Invoice> getInvoicesBetweenDates(DatesDto dates, User loggedUser) throws ValidationException {
        return this.invoiceService.getInvoicesBetweenDates(dates,loggedUser);
    }

    public List getInvoices(Optional<String> numberLine) {
        return this.invoiceService.getInvoices(numberLine);
    }
}
