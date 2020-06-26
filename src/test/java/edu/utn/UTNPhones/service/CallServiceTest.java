package edu.utn.UTNPhones.service;
import edu.utn.UTNPhones.domain.Call;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.dtos.DatesDto;
import edu.utn.UTNPhones.dtos.NewCallDto;
import edu.utn.UTNPhones.exceptions.ValidationException;
import edu.utn.UTNPhones.projections.CallOfUser;
import edu.utn.UTNPhones.projections.TopTenDestinationsByUser;
import edu.utn.UTNPhones.repositories.ICallRepository;
import edu.utn.UTNPhones.services.CallService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
public class CallServiceTest {
    private CallService callService;
    private Call call;
    private List<CallOfUser> list;
    private List<TopTenDestinationsByUser> listTop;
    private ProjectionFactory factory;
    @Mock
    private ICallRepository callRepository;

    @Before
    public void setUp(){
        initMocks(this);
        this.callService = new CallService(callRepository);
        this.call = new Call(null,null,"2262677713",null,null,
                "2262677463",null,null,120, LocalDateTime.parse("2020-05-01T00:00:00"), null, null);
        factory = new SpelAwareProxyProjectionFactory();
        CallOfUser callOfUser = factory.createProjection(CallOfUser.class);
        this.list = Collections.singletonList(callOfUser);
        TopTenDestinationsByUser topTenDestinationsByUser = factory.createProjection(TopTenDestinationsByUser.class);
        this.listTop = Collections.singletonList(topTenDestinationsByUser);
    }

    @Test
    public void createTestBad(){
        assertThrows(ValidationException.class, ()-> {
            callService.create(new NewCallDto("2262677713","2262677713",120, LocalDateTime.parse("2020-05-10T00:00:00")));
        });
    }

    @Test
    public void createTestOk() throws ValidationException {
        when(this.callRepository.save(this.call)).thenReturn(this.call);
        Call responseCall = callService.create(new NewCallDto("2262677713","2262677463",120, LocalDateTime.parse("2020-05-01T00:00:00")));
        assertEquals(responseCall,this.call);
    }

    @Test
    public void getCallOfUserTest(){
        when(callRepository.getCallsOfUser("41923121")).thenReturn(this.list);
        List<CallOfUser> responseList = this.callService.getCallOfUser("41923121");
        assertEquals(responseList,this.list);
        verify(callRepository,times(1)).getCallsOfUser("41923121");
    }

    @Test
    public void getCallsByDatesTest() throws ValidationException {
        when(callRepository.getCallsByDates("2020-05-10T00:00","2020-05-15T00:00", "41923121")).thenReturn(this.list);
        User user = new User();
        user.setIdCard("41923121");
        List<CallOfUser> responseList = this.callService.getCallsByDates(LocalDateTime.parse("2020-05-10T00:00:00"),LocalDateTime.parse("2020-05-15T00:00:00"), user);
        assertEquals(responseList,this.list);
    }

    @Test
    public void getCallsByDatesBadTest(){
        assertThrows(ValidationException.class, ()-> {
            callService.getCallsByDates(LocalDateTime.parse("2020-05-10T00:00:00"),LocalDateTime.parse("2020-05-01T00:00:00"),new User());
        });
    }

    @Test
    public void getTopTenDestinationsTest(){
        when(callRepository.getTopTenDestinationsByUser("41923121")).thenReturn(this.listTop);
        User user = new User();
        user.setIdCard("41923121");
        List<TopTenDestinationsByUser> responseTop = this.callService.getTopTenDestinations(user);
        assertEquals(responseTop,this.listTop);
    }

}
