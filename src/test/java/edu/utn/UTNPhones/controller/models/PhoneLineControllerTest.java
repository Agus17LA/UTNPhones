package edu.utn.UTNPhones.controller.models;
import edu.utn.UTNPhones.controllers.models.PhoneLineController;
import edu.utn.UTNPhones.domain.PhoneLine;
import edu.utn.UTNPhones.services.PhoneLineService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

public class PhoneLineControllerTest {
    private PhoneLineController phoneLineController;
    private PhoneLine phoneLine;
    @Mock
    private PhoneLineService phoneLineService;

    @Before
    public void setUp(){
        initMocks(this);
        this.phoneLineController = new PhoneLineController(phoneLineService);
        this.phoneLine = new PhoneLine(1,null,"2262677713", PhoneLine.LineType.MOBILE, PhoneLine.LineStatus.ACTIVE);
    }

    @Test
    public void createTest(){
        when(phoneLineService.create(new PhoneLine())).thenReturn(this.phoneLine);
        PhoneLine response = phoneLineController.create(new PhoneLine());
        assertEquals(response,this.phoneLine);
        verify(phoneLineService,times(1)).create(new PhoneLine());
    }

    @Test
    public void unsubscribePhoneLineTestOk(){
        when(phoneLineService.unsubscribePhoneLine("123")).thenReturn( new PhoneLine(1,null,"123", PhoneLine.LineType.MOBILE, PhoneLine.LineStatus.INACTIVE));
        Boolean response = phoneLineController.unsubscribePhoneLine("123");
        assertEquals(response,true);
        verify(phoneLineService,times(1)).unsubscribePhoneLine("123");
    }

    @Test
    public void unsubscribePhoneLineTestWrong(){
        when(phoneLineService.unsubscribePhoneLine("123")).thenReturn( new PhoneLine(1,null,"456", PhoneLine.LineType.MOBILE, PhoneLine.LineStatus.ACTIVE));
        Boolean response = phoneLineController.unsubscribePhoneLine("123");
        assertEquals(response,false);
        verify(phoneLineService,times(1)).unsubscribePhoneLine("123");
    }

    @Test
    public void suspendPhoneLineTestOk(){
        when(phoneLineService.suspendPhoneLine("123")).thenReturn( new PhoneLine(1,null,"123", PhoneLine.LineType.MOBILE, PhoneLine.LineStatus.SUSPENDED));
        Boolean response = phoneLineController.suspendPhoneLine("123");
        assertEquals(response,true);
        verify(phoneLineService,times(1)).suspendPhoneLine("123");
    }

    @Test
    public void suspendPhoneLineTestWrong(){
        when(phoneLineService.suspendPhoneLine("123")).thenReturn( new PhoneLine(1,null,"456", PhoneLine.LineType.MOBILE, PhoneLine.LineStatus.ACTIVE));
        Boolean response = phoneLineController.suspendPhoneLine("123");
        assertEquals(response,false);
        verify(phoneLineService,times(1)).suspendPhoneLine("123");
    }

    @Test
    public void getLinesTest(){
        List<PhoneLine> list = new ArrayList<>();
        PhoneLine aux = new PhoneLine();
        list.add(aux);
        when(phoneLineService.getLines(null)).thenReturn(list);
        List<PhoneLine> list2 = new ArrayList<>();
        list2 = phoneLineController.getLines(null);
        assertNotNull(list2);
        assertEquals(list2,list);
        verify(phoneLineService,times(1)).getLines(null);
    }

    @Test
    public void getLineOfUserTest(){
        List<PhoneLine> list = new ArrayList<>();
        list.add(this.phoneLine);
        when(phoneLineService.getLinesOfUser("41923121")).thenReturn(list);
        List<PhoneLine> listResponse = phoneLineController.getLineOfUser("41923121");
        assertEquals(listResponse,list);
        verify(phoneLineService,times(1)).getLinesOfUser("41923121");
    }


}
