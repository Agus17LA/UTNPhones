package edu.utn.UTNPhones.controllers;

import edu.utn.UTNPhones.domain.Call;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.exceptions.ValidationException;
import edu.utn.UTNPhones.projections.MinutesOfCallNewYear2001;
import edu.utn.UTNPhones.services.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calls")
public class CallController {
    @Autowired
    CallService callService;

    @PostMapping("/")
    public Call create(@RequestBody Call call) throws ParamException, ValidationException {
        if(call.verifyNullValues()) throw new ParamException("Missing data");
        return this.callService.create(call);
    }

    @GetMapping(value="/NewYear2001", produces = MediaType.APPLICATION_JSON_VALUE)
    public MinutesOfCallNewYear2001 getMinutesOfNewYear2001() throws DataAccessException {
        return this.callService.getMinutesOfNewYear2001();
    }
}
