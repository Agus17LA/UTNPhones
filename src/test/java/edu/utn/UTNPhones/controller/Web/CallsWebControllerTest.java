package edu.utn.UTNPhones.controller.Web;
import edu.utn.UTNPhones.controllers.models.CallController;
import edu.utn.UTNPhones.controllers.web.CallsWebController;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.dtos.DatesDto;
import edu.utn.UTNPhones.dtos.NewCallDto;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.exceptions.ValidationException;
import edu.utn.UTNPhones.projections.CallOfUser;
import edu.utn.UTNPhones.projections.TopTenDestinationsByUser;
import edu.utn.UTNPhones.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
public class CallsWebControllerTest {
    private CallsWebController callsWebController;
    private User user;
    private ProjectionFactory factory;
    private CallOfUser callOfUser;
    @Mock
    private CallController callController;
    @Mock
    private SessionManager sessionManager;

    @Before
    public void setUp(){
        initMocks(this);
        this.callsWebController = new CallsWebController(callController,sessionManager);
        this.user = new User(1, "41923121", "Agustin", "Lopez", "agezlo", "asdasd", User.UserType.CLIENT, true, null);
        this.factory = new SpelAwareProxyProjectionFactory();
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
    public void getCallsByDatesBad(){
        assertThrows(ParamException.class, ()-> {
            callsWebController.getCallsByDates("123",new DatesDto());
        });
    }

    @Test
    public void getCallsByDatesOk() throws ValidationException, ParamException {
        DatesDto datesDto = new DatesDto(LocalDateTime.parse("2020-05-10T00:00"),LocalDateTime.parse("2020-05-15T00:00"));
        when(sessionManager.getCurrentUser("123")).thenReturn(this.user);
        when(callController.getCallsByDates(datesDto.getFirstDate(),datesDto.getSecondDate(),this.user)).thenReturn(List.of(callOfUser));
        ResponseEntity responseEntity = this.callsWebController.getCallsByDates("123",datesDto);
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(List.of(callOfUser),responseEntity.getBody());
    }

    @Test
    public void getTopTenDestinations(){
        TopTenDestinationsByUser topTenDestinationsByUser = factory.createProjection(TopTenDestinationsByUser.class);
        List<TopTenDestinationsByUser> listTop = Collections.singletonList(topTenDestinationsByUser);
        when(sessionManager.getCurrentUser("123")).thenReturn(this.user);
        when(callController.getTopTenDestinations(this.user)).thenReturn(listTop);
        ResponseEntity responseEntity = this.callsWebController.getTopTenDestinations("123");
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(listTop,responseEntity.getBody());
    }
}
