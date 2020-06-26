package edu.utn.UTNPhones.controller.backoffice;
import edu.utn.UTNPhones.controllers.backoffice.CallsBackController;
import edu.utn.UTNPhones.controllers.models.CallController;
import edu.utn.UTNPhones.domain.Call;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.exceptions.ValidationException;
import edu.utn.UTNPhones.projections.CallOfUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
public class CallsBackControllerTest {
    private CallsBackController callsBackController;
    private CallOfUser callOfUser;
    @Mock
    private CallController callController;
    @Before
    public void setUp(){
        initMocks(this);
        this.callsBackController = new CallsBackController(callController);
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        callOfUser = factory.createProjection(CallOfUser.class);
        callOfUser.setCallDate("2020-05-10T00:00:00");
        callOfUser.setDestinationCall("Mar del Plata");
        callOfUser.setDestinationNumberLine("2231111111");
        callOfUser.setDniUserDest("456");
        callOfUser.setDniUserOrigin("123");
        callOfUser.setDuration(120);
        callOfUser.setFullNameUserDest("Agustin");
        callOfUser.setFullNameUserOrigin("Gumme");
        callOfUser.setInvoice(1);
        callOfUser.setTotalPrice((float)20);
        callOfUser.setOriginNumberLine("2262677713");
    }

    @Test
    public void getCallsOfUserTestBad(){
        assertThrows(ParamException.class, ()-> {
            callsBackController.getCallsOfUser(null);
        });
    }

    @Test
    public void getCallsOfUser() throws ParamException {
        when(callController.getCallsOfUser("41923121")).thenReturn(List.of(this.callOfUser));
        ResponseEntity responseList = this.callsBackController.getCallsOfUser("41923121");
        assertEquals(200,responseList.getStatusCodeValue());
        assertEquals(List.of(this.callOfUser),responseList.getBody());
    }

    @Test
    public void getCallsOfUserNoContent() throws ParamException {
        when(callController.getCallsOfUser("41923121")).thenReturn(List.of());
        ResponseEntity responseList = this.callsBackController.getCallsOfUser("41923121");
        assertEquals(204,responseList.getStatusCodeValue());
        assertEquals(null,responseList.getBody());
    }

}
