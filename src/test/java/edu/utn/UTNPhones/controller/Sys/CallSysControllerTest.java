package edu.utn.UTNPhones.controller.Sys;
import edu.utn.UTNPhones.controllers.models.CallController;
import edu.utn.UTNPhones.controllers.sys.CallSysController;
import edu.utn.UTNPhones.domain.Call;
import edu.utn.UTNPhones.dtos.NewCallDto;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.exceptions.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
public class CallSysControllerTest {
    private CallSysController callSysController;
    @Mock
    private CallController callController;

    @Before
    public void setUp(){
        initMocks(this);
        this.callSysController = new CallSysController(callController);
    }

    @Test
    public void addCallTestBad(){
        assertThrows(ParamException.class, ()-> {
            callSysController.addCall(new NewCallDto());
        });
    }

    @Test
    public void addCallTestOk() throws ValidationException, ParamException {
        NewCallDto newCall = new NewCallDto("2262677713","2262677463",120, LocalDateTime.parse("2020-05-10T00:00"));
        Call call = new Call(1,null,"2262677713",null,null,
                "2262677463",null,null,120, LocalDateTime.parse("2020-05-01T00:00:00"), (float) 5, (float) 10);
        when(callController.create(newCall)).thenReturn(call);
        ResponseEntity responseEntity = this.callSysController.addCall(newCall);
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(call,responseEntity.getBody());
    }
}
