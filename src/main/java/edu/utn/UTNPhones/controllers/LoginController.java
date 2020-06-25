package edu.utn.UTNPhones.controllers;

import edu.utn.UTNPhones.controllers.models.UserController;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.dtos.LoginRequestDto;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.TimeZone;

@RestController
@RequestMapping("/login")
public class LoginController {
    UserController userController;
    SessionManager sessionManager;

    @Autowired
    public LoginController(UserController userController, SessionManager sessionManager) {
        this.userController = userController;
        this.sessionManager = sessionManager;
    }

    @PostMapping("/")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto) throws ParamException, NotExistException {
        System.out.println(TimeZone.getDefault()+"  ----  "+TimeZone.getAvailableIDs());
        ResponseEntity response;
        try {
            User u = userController.login(loginRequestDto);
            String token = sessionManager.createSession(u);
            response = ResponseEntity.ok().headers(createHeaders(token)).build();
        } catch (ParamException e) {
            throw new ParamException(e.getMessage());
        } catch (NotExistException e){
            throw new NotExistException(e.getMessage());
        }
        return response;
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
        sessionManager.removeSession(token);
        return ResponseEntity.ok().build();
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        return responseHeaders;
    }
}
