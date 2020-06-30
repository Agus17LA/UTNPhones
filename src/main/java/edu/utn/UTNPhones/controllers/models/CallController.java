package edu.utn.UTNPhones.controllers.models;

import edu.utn.UTNPhones.domain.Call;

import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.dtos.NewCallDto;
import edu.utn.UTNPhones.exceptions.ValidationException;
import edu.utn.UTNPhones.projections.CallOfUser;
import edu.utn.UTNPhones.projections.Calls;
import edu.utn.UTNPhones.projections.TopTenDestinationsByUser;
import edu.utn.UTNPhones.services.CallService;
import edu.utn.UTNPhones.services.PhoneLineService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@Controller
public class CallController {
    @Autowired
    CallService callService;
    @Autowired
    PhoneLineService phoneLineService;

    public Call create(NewCallDto call) throws ValidationException {
        return this.callService.create(call);
    }

    public List<CallOfUser> getCallsOfUser(String dni) {
        return callService.getCallOfUser(dni);
    }

    public List<Calls> getCallsByDates(LocalDateTime firstDate, LocalDateTime secondDate, User loggedUser) throws ValidationException {
        return callService.getCallsByDates(firstDate,secondDate,this.phoneLineService.getLinesOfUser(loggedUser.getIdCard()));
    }

    public List<TopTenDestinationsByUser> getTopTenDestinations(User loggedUser) {
        return callService.getTopTenDestinations(loggedUser);
    }
}
