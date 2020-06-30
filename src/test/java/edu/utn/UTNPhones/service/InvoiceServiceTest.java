package edu.utn.UTNPhones.service;
import edu.utn.UTNPhones.domain.Invoice;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.dtos.DatesDto;
import edu.utn.UTNPhones.dtos.LoginRequestDto;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.exceptions.ValidationException;
import edu.utn.UTNPhones.repositories.IInvoiceRepository;
import edu.utn.UTNPhones.services.InvoiceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
public class InvoiceServiceTest {
    private InvoiceService invoiceService;
    private Invoice invoice;
    private User user;
    @Mock
    private IInvoiceRepository invoiceRepository;

    @Before
    public void setUp(){
        initMocks(this);
        this.invoiceService = new InvoiceService(invoiceRepository);
        this.invoice = new Invoice(1,"2262677713",20,LocalDateTime.parse("2020-05-10T00:00:00"),
                LocalDateTime.parse("2020-05-25T00:00:00"),(float)5.30,(float)10.30,true);
        this.user = new User(1, "41923121", "Agustin", "Lopez", "agezlo", "asdasd", User.UserType.CLIENT, true, null);
    }

    @Test
    public void getInvoicesBetweenDates() throws ValidationException {
        List<Invoice> list = new ArrayList<>();
        list.add(invoice);
        when(invoiceRepository.getInvoicesBetweenDates("2020-05-10T00:00:00",
                "2020-06-15T00:00:00",1)).thenReturn(list);
        List<Invoice> listResponse = this.invoiceService.getInvoicesBetweenDates(new DatesDto(LocalDateTime.parse("2020-05-10T00:00:00"),
                LocalDateTime.parse("2020-06-15T00:00:00")),this.user);
        assertEquals(listResponse,list);
    }

    @Test
    public void invoicesBetweenDatesException(){
        assertThrows(ValidationException.class, ()-> {
            invoiceService.getInvoicesBetweenDates(new DatesDto(LocalDateTime.parse("2020-05-10T00:00:00"),
                    LocalDateTime.parse("2020-04-15T00:00:00")),this.user);
        });
    }

    @Test
    public void getInvoiceTest(){
        List invoice = List.of(this.invoice);
        when(invoiceRepository.findAllByNumberLine("2262677713")).thenReturn(Optional.of(invoice));
        List response = invoiceService.getInvoices(Optional.of("2262677713"));
        assertEquals(response,invoice);
        verify(invoiceRepository,times(1)).findAllByNumberLine("2262677713");
    }

    @Test
    public void getInvoicesTest(){
        List invoice = List.of(this.invoice);
        when(invoiceRepository.findAll()).thenReturn(invoice);
        List response = invoiceService.getInvoices(Optional.empty());
        assertEquals(response,invoice);
        verify(invoiceRepository,times(1)).findAll();
    }
//
//    @Test
//    public void getInvoicesTest(){
//        List<User> userList = Collections.singletonList(this.user);
//        when(userRepository.findAll()).thenReturn(userList);
//        List responseUserList = userService.getUsers(null);
//        assertEquals(responseUserList,userList);
//        verify(userRepository,times(1)).findAll();
//    }




//    LoginRequestDto loginRequestDto = new LoginRequestDto("41923121","agezlo");
//    when(userService.login(loginRequestDto.getUsername(),loginRequestDto.getPassword())).thenThrow(new NotExistException("User not exists"));
//    assertThrows(NotExistException.class, ()-> {
//        userController.login(loginRequestDto);
//    });

}
