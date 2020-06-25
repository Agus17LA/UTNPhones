package edu.utn.UTNPhones.controllers.web;

import edu.utn.UTNPhones.controllers.models.InvoiceController;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.dtos.DatesDto;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.exceptions.ValidationException;
import edu.utn.UTNPhones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web/invoices")
public class InvoicesWebController {
    InvoiceController invoiceController;
    SessionManager sessionManager;

    @Autowired
    public InvoicesWebController(InvoiceController invoiceController, SessionManager sessionManager) {
        this.invoiceController = invoiceController;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/")
    public ResponseEntity getInvoicesBetweenDates(@RequestHeader("Authorization") String sessionToken, @RequestBody DatesDto dates) throws ParamException, ValidationException {
        if(dates.getFirstDate() == null || dates.getSecondDate() == null) throw new ParamException("You must enter both dates");
        User loggedUser = sessionManager.getCurrentUser(sessionToken);
        return ResponseEntity.ok().body(invoiceController.getInvoicesBetweenDates(dates,loggedUser));
    }

}
