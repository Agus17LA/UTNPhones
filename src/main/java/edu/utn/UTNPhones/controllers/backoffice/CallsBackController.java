package edu.utn.UTNPhones.controllers.backoffice;

import edu.utn.UTNPhones.controllers.models.CallController;
import edu.utn.UTNPhones.exceptions.ParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/backoffice/calls")
public class CallsBackController {
    @Autowired
    CallController callController;

    @GetMapping("/{dni}")
    public ResponseEntity getCallsOfUser(@PathVariable String dni) throws ParamException {
        if (dni == null) throw new ParamException("You must specify an dni");
        List callsOfUser = callController.getCallsOfUser(dni);
        if(callsOfUser.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok().body(callsOfUser);
    }

}
