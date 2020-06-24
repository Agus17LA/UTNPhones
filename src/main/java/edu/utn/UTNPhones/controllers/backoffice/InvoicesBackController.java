package edu.utn.UTNPhones.controllers.backoffice;

import edu.utn.UTNPhones.controllers.domain.InvoiceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/backoffice/invoices")
public class InvoicesBackController {
    @Autowired
    InvoiceController invoiceController;

    @GetMapping("/{numberLine}")
    public ResponseEntity getInvoices(@PathVariable(required = false) Optional<String> numberLine){
        List invoices = invoiceController.getInvoices(numberLine);
        if(invoices.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok().body(invoices);
    }
}
