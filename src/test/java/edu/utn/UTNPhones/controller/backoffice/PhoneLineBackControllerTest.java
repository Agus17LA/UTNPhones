package edu.utn.UTNPhones.controller.backoffice;
import edu.utn.UTNPhones.controllers.backoffice.PhoneLinesBackController;
import edu.utn.UTNPhones.controllers.models.PhoneLineController;
import edu.utn.UTNPhones.domain.PhoneLine;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.exceptions.ParamException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
public class PhoneLineBackControllerTest {
    private PhoneLinesBackController phoneLinesBackController;
    @Mock
    private PhoneLineController phoneLineController;

    @Before
    public void setUp(){
        initMocks(this);
        this.phoneLinesBackController = new PhoneLinesBackController(phoneLineController);
    }

    @Test
    public void addPhoneLineTestBad(){
        assertThrows(ParamException.class, ()-> {
            phoneLinesBackController.addPhoneLine(new PhoneLine());
        });
    }

    @Test
    public void addPhoneLineTestOk() throws ParamException {
        PhoneLine phoneLine = new PhoneLine(1,new User(),"2262677713", PhoneLine.LineType.MOBILE, PhoneLine.LineStatus.ACTIVE);
        when(phoneLineController.create(phoneLine)).thenReturn(phoneLine);
        ResponseEntity response = this.phoneLinesBackController.addPhoneLine(phoneLine);
        assertEquals(201,response.getStatusCodeValue());
    }

    @Test
    public void unsubscribePhoneLineTestBad(){
        assertThrows(ParamException.class, ()-> {
            phoneLinesBackController.unsubscribePhoneLine(null);
        });
    }

    @Test
    public void unsubscribePhoneLineTestOk() throws ParamException {
        when(phoneLineController.unsubscribePhoneLine("2262677713")).thenReturn(true);
        ResponseEntity response = this.phoneLinesBackController.unsubscribePhoneLine("2262677713");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void suspendPhoneLineTestBad(){
        assertThrows(ParamException.class, ()-> {
            phoneLinesBackController.suspendPhoneLine(null);
        });
    }

    @Test
    public void suspendPhoneLineTestOk() throws ParamException {
        when(phoneLineController.suspendPhoneLine("2262677713")).thenReturn(true);
        ResponseEntity response = this.phoneLinesBackController.suspendPhoneLine("2262677713");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void modifyPhoneLineTestBad(){
        Map<String, Object> map = new HashMap<>();
        map.put("lineType", "MOBILE");
        assertThrows(ParamException.class, ()-> {
            phoneLinesBackController.modifyPhoneLine(null, map);
        });
    }

    @Test
    public void modifyPhoneLineTestOk() throws ParamException {
        Map<String, Object> map = new HashMap<>();
        map.put("lineType", "MOBILE");
        PhoneLine phoneLine = new PhoneLine(1,new User(),"2262677713", PhoneLine.LineType.LANDLINE, PhoneLine.LineStatus.ACTIVE);
        when(phoneLineController.update("2262677713",map)).thenReturn(phoneLine);
        ResponseEntity response = this.phoneLinesBackController.modifyPhoneLine("2262677713",map);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getPhoneLinesTestEmpty(){
        when(phoneLineController.getLines(Optional.of("2262677713"))).thenReturn(List.of());
        ResponseEntity response = this.phoneLinesBackController.getPhoneLines(Optional.empty());
        assertEquals(204,response.getStatusCodeValue());
    }

    @Test
    public void getPhoneLinesTest(){
        List lines = Collections.singletonList(new PhoneLine(1,new User(),"2262677713", PhoneLine.LineType.LANDLINE, PhoneLine.LineStatus.ACTIVE));
        when(phoneLineController.getLines(Optional.empty())).thenReturn(lines);
        ResponseEntity response = this.phoneLinesBackController.getPhoneLines(Optional.empty());
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(lines,response.getBody());
    }

    @Test
    public void getPhoneLinesOfUserTestBad(){
        assertThrows(ParamException.class, ()-> {
            phoneLinesBackController.getPhoneLinesOfUser(null);
        });
    }

    @Test
    public void getPhoneLinesOfUserTestEmpty() throws ParamException {
        when(phoneLineController.getLineOfUser("41923121")).thenReturn(List.of());
        ResponseEntity response = this.phoneLinesBackController.getPhoneLinesOfUser("41923121");
        assertEquals(204,response.getStatusCodeValue());
    }

    @Test
    public void getPhoneLinesOfUserTestOk() throws ParamException {
        List lines = Collections.singletonList(new PhoneLine(1,new User(),"2262677713", PhoneLine.LineType.LANDLINE, PhoneLine.LineStatus.ACTIVE));
        when(phoneLineController.getLineOfUser("41923121")).thenReturn(lines);
        ResponseEntity response = this.phoneLinesBackController.getPhoneLinesOfUser("41923121");
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(lines,response.getBody());
    }


}
