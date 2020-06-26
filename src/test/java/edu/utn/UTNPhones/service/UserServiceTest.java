package edu.utn.UTNPhones.service;
import edu.utn.UTNPhones.domain.City;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.dtos.DatesDto;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.exceptions.ValidationException;
import edu.utn.UTNPhones.repositories.IUserRepository;
import edu.utn.UTNPhones.services.CityService;
import edu.utn.UTNPhones.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
public class UserServiceTest {
    private UserService userService;
    private User user;
    @Mock
    private IUserRepository userRepository;
    @Mock
    private CityService cityService;

    @Before
    public void setUp(){
        initMocks(this);
        this.userService = new UserService(userRepository,cityService);
        this.user = new User(1, "41923121", "Agustin", "Lopez", "agezlo", "asdasd", User.UserType.CLIENT, true, null);
    }

    @Test
    public void loginTest() throws NotExistException {
        when(userRepository.getByUsernameAndPassword("agezlo","asdasd")).thenReturn(this.user);
        User responseUser = this.userService.login("agezlo","asdasd");
        assertEquals(responseUser,this.user);
        verify(userRepository,times(1)).getByUsernameAndPassword("agezlo","asdasd");
    }

    @Test
    public void loginNotExistsTest() throws NotExistException {
        when(userRepository.getByUsernameAndPassword("a","b")).thenReturn(null);
        assertThrows(NotExistException.class, ()-> {
            userService.login("a","b");
        });
    }

    @Test
    public void getUserTest(){
        Optional<User> user = Optional.ofNullable(this.user);
        when(userRepository.getByIdCard("41923121")).thenReturn(user);
        List responseUser = this.userService.getUsers("41923121");
        assertEquals(responseUser.get(0),user.get());
        verify(userRepository,times(1)).getByIdCard("41923121");
    }

    @Test
    public void getUsersTest(){
        List<User> userList = Collections.singletonList(this.user);
        when(userRepository.findAll()).thenReturn(userList);
        List responseUserList = userService.getUsers(null);
        assertEquals(responseUserList,userList);
        verify(userRepository,times(1)).findAll();
    }

    @Test
    public void addClientTest(){
        when(userRepository.save(new User())).thenReturn(this.user);
        User responseUser = this.userService.addClient(new User());
        assertEquals(responseUser,this.user);
        verify(userRepository,times(1)).save(new User());
    }

    @Test
    public void changeStatusTest(){
        when(userRepository.getByIdCard("41923121")).thenReturn(Optional.ofNullable(this.user));
        when(userRepository.save(this.user)).thenReturn(this.user);
        User responseUser = userService.changeStatus("41923121", true);
        assertEquals(responseUser,Optional.ofNullable(this.user).get());
        verify(userRepository,times(1)).getByIdCard("41923121");
        verify(userRepository,times(1)).save(this.user);
    }

    @Test
    public void getClientTest(){
        Optional<User> client = Optional.ofNullable(this.user);
        when(userRepository.findByIdCardAndUserType("41923121", User.UserType.CLIENT)).thenReturn(client);
        List responseUser = this.userService.getClients(Optional.of("41923121"));
        assertEquals(responseUser.get(0),client.get());
        verify(userRepository,times(1)).findByIdCardAndUserType("41923121",User.UserType.CLIENT);
    }

    @Test
    public void getClientsTest(){
        List<User> userList = Collections.singletonList(this.user);
        when(userRepository.findAllByUserType(User.UserType.CLIENT)).thenReturn(userList);
        List responseUserList = userService.getClients(Optional.empty());
        assertEquals(responseUserList,userList);
        verify(userRepository,times(1)).findAllByUserType(User.UserType.CLIENT);
    }

    @Test
    public void updateTest(){
        when(userRepository.getByIdCard("41923121")).thenReturn(Optional.ofNullable(this.user));
        when(userRepository.save(this.user)).thenReturn(this.user);
        User responseUser = userService.update("41923121", anyMap());
        assertEquals(responseUser,this.user);
    }

    @Test
    public void updateMapTest(){
        Map<String, Object> map = new HashMap<>();
        map.put("dni", "41923121");
        map.put("name", "Agustin");
        map.put("surname", "Lopez");
        map.put("username", "agezlo");
        map.put("password", "asdasd");
        map.put("userType", "CLIENT");
        map.put("userStatus", true);
        map.put("city", 1);

        Map<String, Object> expected = new HashMap<>();
        expected.put("dni", "41923121");
        expected.put("name", "Agustin");
        expected.put("surname", "Lopez");
        expected.put("username", "agezlo");
        expected.put("password", "asdasd");
        expected.put("userType", "CLIENT");
        expected.put("userStatus", true);
        expected.put("city", 1);

        List<City> city = Collections.singletonList(new City(1,null,"Necochea",2262));
        when(cityService.getCities(1)).thenReturn(city);
        when(userRepository.getByIdCard("41923121")).thenReturn(Optional.ofNullable(this.user));
        when(userRepository.save(this.user)).thenReturn(this.user);
        User responseUser = userService.update("41923121", map);
        assertEquals(responseUser,this.user);
        assertThat(map, is(expected));
    }




}
