package edu.utn.UTNPhones.controller.models;

import edu.utn.UTNPhones.controllers.models.UserController;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.dtos.LoginRequestDto;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

public class UserControllerTest {
    private UserController userController;
    private User user;
    @Mock
    private UserService userService;

    @Before
    public void setUp(){
        initMocks(this);
        this.userController= new UserController(this.userService);
        this.user = new User(1, "41923121", "Agustin", "Lopez", "agezlo", "asdasd", User.UserType.CLIENT, true, null);
    }

    @Test
    public void addClientOk(){
        when(userService.addClient(new User())).thenReturn(this.user);
        User userResponse = userController.addClient(new User());
        assertEquals(userResponse,this.user);
        verify(userService,times(1)).addClient(new User());
    }

    @Test
    public void changeStatusOk(){
        when(userService.changeStatus("41923121",true)).thenReturn(this.user);
        User userResponse = userController.changeStatus("41923121",true);
        assertEquals(userResponse,this.user);
        verify(userService,times(1)).changeStatus("41923121",true);
    }

    @Test
    public void getClients(){
        List<User> list = new ArrayList<>();
        User aux = new User();
        list.add(aux);
        when(userService.getClients(null)).thenReturn(list);
        List<User> list2 = new ArrayList<>();
        list2 = userController.getClients(null);
        assertNotNull(list2);
        assertEquals(list2,list);
        verify(userService,times(1)).getClients(null);
    }

    @Test
    public void loginOk() throws ParamException, NotExistException{
        LoginRequestDto loginRequestDto = new LoginRequestDto("41923121","agezlo");
        when(userService.login(loginRequestDto.getUsername(),loginRequestDto.getPassword())).thenReturn(this.user);
        User response = userController.login(loginRequestDto);
        assertEquals(this.user.getIdCard(), response.getIdCard());
        assertEquals(this.user.getPassword(), response.getPassword());
        verify(userService,times(1)).login(loginRequestDto.getUsername(),loginRequestDto.getPassword());
    }

    @Test
    public void loginNotFoundUser() throws NotExistException{
        LoginRequestDto loginRequestDto = new LoginRequestDto("41923121","agezlo");
        when(userService.login(loginRequestDto.getUsername(),loginRequestDto.getPassword())).thenThrow(new NotExistException("User not exists"));
        assertThrows(NotExistException.class, ()-> {
            userController.login(loginRequestDto);
        });
    }

    @Test
    public void loginParamException() throws ParamException, NotExistException{
        assertThrows(ParamException.class, ()-> {
           userController.login(new LoginRequestDto(null,null));
        });
    }

    @Test
    public void loginParamException2() throws ParamException, NotExistException{
        assertThrows(ParamException.class, ()-> {
            userController.login(new LoginRequestDto(null,"asdasd"));
        });
    }

    @Test
    public void loginParamException3() throws ParamException, NotExistException{
        assertThrows(ParamException.class, ()-> {
            userController.login(new LoginRequestDto("41923121",null));
        });
    }

//    @Test
//    public void update(){
//        doNothing().when(userService.update("41923121", anyMap()));
//        userController.update("41923121", anyMap());
//        verify(userService,times(1)).update("41923121", anyMap());
//    }



}
