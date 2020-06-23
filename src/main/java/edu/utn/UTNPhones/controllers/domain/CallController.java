package edu.utn.UTNPhones.controllers.domain;

import edu.utn.UTNPhones.domain.Call;

import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.exceptions.ValidationException;
import edu.utn.UTNPhones.projections.CallOfUser;
import edu.utn.UTNPhones.projections.MinutesOfCallNewYear2001;
import edu.utn.UTNPhones.projections.TopTenDestinationsByUser;
import edu.utn.UTNPhones.services.CallService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
public class CallController {
    @Autowired
    CallService callService;

    public Call create(@RequestBody Call call) throws ParamException, ValidationException {
        if(call.verifyNullValues()) throw new ParamException("Missing data");
        return this.callService.create(call);
    }

    public List<CallOfUser> getCallsOfUser(String dni) {
        return callService.getCallOfUser(dni);
    }

    public List<CallOfUser> getCallsByDates(LocalDateTime firstDate, LocalDateTime secondDate, User loggedUser) throws ValidationException {
        return callService.getCallsByDates(firstDate,secondDate,loggedUser);
    }

    public List<TopTenDestinationsByUser> getTopTenDestinations(User loggedUser) {
        return callService.getTopTenDestinations(loggedUser);
    }
}
