package edu.utn.UTNPhones.controllers.models;

import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.dtos.LoginRequestDto;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    public User login(LoginRequestDto loginRequestDto) throws ParamException, NotExistException {
        if(loginRequestDto.getUsername() == null || loginRequestDto.getPassword() == null){
            throw new ParamException("User attributes cannot be null");
        }else{
            return this.userService.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        }
    }

    public User addClient(User client){
        return this.userService.addClient(client);
    }

    public User changeStatus(String dni, boolean status) {
        return this.userService.changeStatus(dni,status);
    }

    public User update(String dni, Map<String, Object> changes) {
        return this.userService.update(dni,changes);
    }

    public List<User> getClients(Optional<String> clientDni) {
        return this.userService.getClients(clientDni);
    }

    /* ------------------------------------------------------------------------------------------------------------ */

//    @PostMapping("/")
//    public User create(@RequestBody User user) throws ParamException {
//        if(user.verifyNullValues()) throw new ParamException("User attributes cannot be null");
//        return this.userService.create(user);
//    }
//
//    @DeleteMapping("/{userId}")
//    public void delete(@PathVariable Integer userId) throws ParamException, NotExistException {
//        if(userId == null) throw new ParamException("You must specify an id");
//        this.userService.logicDelete(userId);
//    }
//
//    @GetMapping("/")
//    public List<User> getUsers(@RequestParam(value="userId",required=false) Integer userId) throws EmptyListException {
//        List users = this.userService.getUsers(userId);
//        if(users.isEmpty()) throw new EmptyListException("Empty list of users");
//        return users;
//    }
//
//    @PutMapping("/")
//    public void update(@RequestBody User userUpdated) throws NotExistException {
//        this.userService.update(userUpdated);
//    }





}
