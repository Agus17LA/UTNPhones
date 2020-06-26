package edu.utn.UTNPhones.controller.backoffice;
import edu.utn.UTNPhones.controllers.backoffice.InvoicesBackController;
import edu.utn.UTNPhones.controllers.models.InvoiceController;
import edu.utn.UTNPhones.domain.City;
import edu.utn.UTNPhones.domain.Fare;
import edu.utn.UTNPhones.domain.Invoice;
import edu.utn.UTNPhones.exceptions.NotExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
public class InvoicesBackControllerTest {
    private InvoicesBackController invoicesBackController;
    @Mock
    private InvoiceController invoiceController;

    @Before
    public void setUp(){
        initMocks(this);
        this.invoicesBackController = new InvoicesBackController(invoiceController);
    }

    @Test
    public void getInvoicesTestEmpty() throws NotExistException {
        when(invoiceController.getInvoices(Optional.of("2262677713"))).thenReturn(List.of());
        ResponseEntity responseEntity = this.invoicesBackController.getInvoices(Optional.of("2262677713"));
        assertEquals(204,responseEntity.getStatusCodeValue());
    }

    @Test
    public void getInvoicesTest() throws NotExistException {
        List invoices = Collections.singletonList(new Invoice(1,"2262677713",25, LocalDateTime.parse("2020-05-01T00:00:00"),LocalDateTime.parse("2020-05-15T23:59:59"),Float.valueOf("120.5"),Float.valueOf("145.5"),true));
        when(invoiceController.getInvoices(Optional.of("2262677713"))).thenReturn(invoices);
        ResponseEntity responseEntity = this.invoicesBackController.getInvoices(Optional.of("2262677713"));
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(invoices,responseEntity.getBody());
    }
}
