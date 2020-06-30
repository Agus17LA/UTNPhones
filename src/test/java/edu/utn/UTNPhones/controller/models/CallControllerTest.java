package edu.utn.UTNPhones.controller.models;
import edu.utn.UTNPhones.controllers.models.CallController;
import edu.utn.UTNPhones.domain.Call;
import edu.utn.UTNPhones.domain.PhoneLine;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.dtos.NewCallDto;
import edu.utn.UTNPhones.exceptions.ValidationException;
import edu.utn.UTNPhones.projections.CallOfUser;
import edu.utn.UTNPhones.projections.Calls;
import edu.utn.UTNPhones.projections.TopTenDestinationsByUser;
import edu.utn.UTNPhones.services.CallService;
import edu.utn.UTNPhones.services.PhoneLineService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
public class CallControllerTest {
    private CallController callController;

    private Call call;
    private List<CallOfUser> list;
    private ProjectionFactory factory;
    @Mock
    private CallService callService;
    @Mock
    private PhoneLineService phoneLineService;

    @Before
    public void setUp(){
        initMocks(this);
        this.callController = new CallController(callService,phoneLineService);
        this.call = new Call(1,null,"2262677713",null,null,
                "2262677463",null,null,120, LocalDateTime.parse("2020-05-01T00:00:00"), (float) 5, (float) 10);
        factory = new SpelAwareProxyProjectionFactory();
        CallOfUser callOfUser = factory.createProjection(CallOfUser.class);
        this.list = Collections.singletonList(callOfUser);
    }

    @Test
    public void createTest() throws ValidationException {
        when(callService.create(new NewCallDto())).thenReturn(this.call);
        Call callResponse = callController.create(new NewCallDto());
        assertEquals(callResponse,this.call);
        verify(callService,times(1)).create(new NewCallDto());
    }

    @Test
    public void getCallsOfUserTest(){
        when(callService.getCallOfUser("41923121")).thenReturn(list);
        List<CallOfUser> responseCallOfUser = callController.getCallsOfUser("41923121");
        assertEquals(responseCallOfUser,list);
        verify(callService,times(1)).getCallOfUser("41923121");
    }

    @Test
    public void getCallsByDatesTest() throws ValidationException {
        User user = new User(1,"41923121","Agustin","Lopez","agezlo","asdasd", User.UserType.CLIENT,true,null);
        List phoneLines = Collections.singletonList(new PhoneLine(1,user,"2262677713", PhoneLine.LineType.MOBILE, PhoneLine.LineStatus.ACTIVE));
        Calls calls = factory.createProjection(Calls.class);
        when(callService.getCallsByDates(null,null,phoneLines)).thenReturn(List.of(calls));
        when(phoneLineService.getLinesOfUser("41923121")).thenReturn(phoneLines);
        List<Calls> responseCallOfUser = callController.getCallsByDates(null,null,user);
        assertEquals(List.of(calls),responseCallOfUser);
        verify(callService,times(1)).getCallsByDates(null,null,phoneLines);
    }

    @Test
    public void getTopTenDestinationsTest(){
        TopTenDestinationsByUser topTenDestinationsByUser = factory.createProjection(TopTenDestinationsByUser.class);
        List<TopTenDestinationsByUser> listTop = Collections.singletonList(topTenDestinationsByUser);
        when(callService.getTopTenDestinations(new User())).thenReturn(listTop);
        List<TopTenDestinationsByUser> responseList = callController.getTopTenDestinations(new User());
        assertEquals(responseList,listTop);
        verify(callService,times(1)).getTopTenDestinations(new User());
    }
}
