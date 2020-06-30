package edu.utn.UTNPhones.controllers.web;

import edu.utn.UTNPhones.controllers.models.CallController;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.dtos.DatesDto;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.exceptions.ValidationException;
import edu.utn.UTNPhones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web/calls")
public class CallsWebController {
    CallController callController;
    SessionManager sessionManager;

    @Autowired
    public CallsWebController(CallController callController, SessionManager sessionManager) {
        this.callController = callController;
        this.sessionManager = sessionManager;
    }

    @PostMapping("/")
    public ResponseEntity getCallsByDates(@RequestHeader("Authorization") String sessionToken, @RequestBody DatesDto dates) throws ParamException, ValidationException {
        if(dates.getFirstDate() == null || dates.getSecondDate() == null) throw new ParamException("You must enter both dates");
        User loggedUser = sessionManager.getCurrentUser(sessionToken);
        List calls = callController.getCallsByDates(dates.getFirstDate(),dates.getSecondDate(),loggedUser);
        if(calls.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(calls);
    }

    @GetMapping("/")
    public ResponseEntity getTopTenDestinations(@RequestHeader("Authorization") String sessionToken){
        User loggedUser = sessionManager.getCurrentUser(sessionToken);
        List top = callController.getTopTenDestinations(loggedUser);
        if(top.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(top);
    }
}

