package edu.utn.UTNPhones.controller.backoffice;
import edu.utn.UTNPhones.controllers.backoffice.ClientsBackController;
import edu.utn.UTNPhones.controllers.models.UserController;
import edu.utn.UTNPhones.domain.City;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.exceptions.ParamException;
import org.apache.coyote.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
public class ClientsBackControllerTest {
    private ClientsBackController clientsBackController;
    private User user;
    @Mock
    private UserController userController;

    @Before
    public void setUp(){
        initMocks(this);
        this.clientsBackController = new ClientsBackController(userController);
        this.user = new User(1, "41923121", "Agustin", "Lopez", "agezlo", "asdasd", User.UserType.CLIENT, true, new City());
    }

    @Test
    public void addClientTestBad(){
        assertThrows(ParamException.class, ()-> {
            clientsBackController.addClient(new User());
        });
    }

    @Test
    public void addClientOk() throws ParamException {
        when(userController.addClient(this.user)).thenReturn(this.user);
        ResponseEntity response = this.clientsBackController.addClient(this.user);
        assertEquals(201,response.getStatusCodeValue());
        assertEquals(this.user,response.getBody());
    }

    @Test
    public void logicDeleteClientTestBad(){
        assertThrows(ParamException.class, ()-> {
            clientsBackController.logicDeleteClient(null);
        });
    }

    @Test
    public void logicDeleteClientTestOk() throws ParamException {
        when(userController.changeStatus("41923121", false)).thenReturn(this.user);
        ResponseEntity response = this.clientsBackController.logicDeleteClient("41923121");
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(this.user,response.getBody());
    }

    @Test
    public void modifyClientTestBad(){
        Map<String, Object> map = new HashMap<>();
        map.put("dni", "41923121");
        map.put("name", "Agustin");
        map.put("surname", "Lopez");
        map.put("username", "agezlo");
        map.put("password", "asdasd");
        map.put("userType", "CLIENT");
        map.put("userStatus", true);
        map.put("city", 1);
        assertThrows(ParamException.class, ()-> {
            clientsBackController.modifyClient(null, map);
        });
    }

    @Test
    public void modifyClientTest() throws ParamException {
        Map<String, Object> map = new HashMap<>();
        map.put("dni", "41923121");
        map.put("name", "Agustin");
        map.put("surname", "Lopez");
        map.put("username", "agezlo");
        map.put("password", "asdasd");
        map.put("userType", "CLIENT");
        map.put("userStatus", true);
        map.put("city", 1);
        when(userController.update("41923121",map)).thenReturn(this.user);
        ResponseEntity response = this.clientsBackController.modifyClient("41923121",map);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(this.user,response.getBody());
    }

    @Test
    public void getClientsTest(){
        when(userController.getClients(Optional.of("41923121"))).thenReturn(List.of(this.user));
        ResponseEntity response = this.clientsBackController.getClients(Optional.of("41923121"));
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(List.of(this.user),response.getBody());
    }

    @Test
    public void getClientsTestEmpty(){
        when(userController.getClients(Optional.of("41923121"))).thenReturn(List.of());
        ResponseEntity response = this.clientsBackController.getClients(Optional.of("41923121"));
        assertEquals(204,response.getStatusCodeValue());
        assertEquals(null,response.getBody());
    }

}
