package edu.utn.UTNPhones.controller.models;

import edu.utn.UTNPhones.controllers.models.InvoiceController;
import edu.utn.UTNPhones.domain.Invoice;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.dtos.DatesDto;
import edu.utn.UTNPhones.exceptions.ValidationException;
import edu.utn.UTNPhones.services.InvoiceService;
import org.apache.tomcat.jni.Local;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

public class InvoiceControllerTest {
    private InvoiceController invoiceController;
    private Invoice invoice;
    @Mock
    private InvoiceService invoiceService;

    @Before
    public void setUp(){
        initMocks(this);
        this.invoiceController = new InvoiceController(invoiceService);
        this.invoice = new Invoice(1,"2262677713",25,LocalDateTime.parse("2020-05-01T00:00:00"),LocalDateTime.parse("2020-05-15T23:59:59"),Float.valueOf("120.5"),Float.valueOf("145.5"),true);
    }

    @Test
    public void getInvoicesBetweenDatesTest() throws ValidationException {
        //DatesDto datesDto = new DatesDto(LocalDateTime.parse("2020-05-01T00:00:00"),LocalDateTime.parse("2020-05-15T23:59:59"));
        List<Invoice> list = new ArrayList<>();
        list.add(this.invoice);
        when(invoiceService.getInvoicesBetweenDates(new DatesDto(),new User())).thenReturn(list);
        List<Invoice> responseList = invoiceController.getInvoicesBetweenDates(new DatesDto(),new User());
        assertEquals(responseList,list);
        verify(invoiceService,times(1)).getInvoicesBetweenDates(new DatesDto(),new User());
    }

    @Test
    public void getInvoicesTest(){
        List list = Collections.singletonList(this.invoice);
        when(invoiceService.getInvoices(null)).thenReturn(list);
        List<Invoice> responseList = invoiceController.getInvoices(null);
        assertEquals(responseList,list);
        verify(invoiceService,times(1)).getInvoices(null);
    }



}
