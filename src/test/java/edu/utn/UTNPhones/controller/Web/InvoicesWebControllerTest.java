package edu.utn.UTNPhones.controller.Web;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import edu.utn.UTNPhones.controllers.models.InvoiceController;
import edu.utn.UTNPhones.controllers.web.InvoicesWebController;
import edu.utn.UTNPhones.domain.Invoice;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.dtos.DatesDto;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.exceptions.ValidationException;
import edu.utn.UTNPhones.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
public class InvoicesWebControllerTest {
    private InvoicesWebController invoicesWebController;
    @Mock
    private SessionManager sessionManager;
    @Mock
    private InvoiceController invoiceController;
    private User user;

    @Before
    public void setUp(){
        initMocks(this);
        this.invoicesWebController = new InvoicesWebController(invoiceController,sessionManager);
        this.user = new User(1, "41923121", "Agustin", "Lopez", "agezlo", "asdasd", User.UserType.CLIENT, true, null);
    }

    @Test
    public void getInvoicesBetweenDatesTestBad(){
        assertThrows(ParamException.class, ()-> {
            invoicesWebController.getInvoicesBetweenDates("123",new DatesDto());
        });
    }

    @Test
    public void getInvoicesBetweenDatesTest() throws ValidationException, ParamException {
        DatesDto datesDto = new DatesDto(LocalDateTime.parse("2020-05-10T00:00"),LocalDateTime.parse("2020-05-15T00:00"));
        Invoice invoice =  new Invoice(1,"2262677713",25,LocalDateTime.parse("2020-05-01T00:00:00"),LocalDateTime.parse("2020-05-15T23:59:59"),Float.valueOf("120.5"),Float.valueOf("145.5"),true);
        when(sessionManager.getCurrentUser("123")).thenReturn(this.user);
        when(invoiceController.getInvoicesBetweenDates(datesDto,this.user)).thenReturn(List.of(invoice));
        ResponseEntity responseEntity = this.invoicesWebController.getInvoicesBetweenDates("123",datesDto);
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(List.of(invoice),responseEntity.getBody());
    }

}
