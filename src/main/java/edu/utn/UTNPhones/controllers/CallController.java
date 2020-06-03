package edu.utn.UTNPhones.controllers;

import edu.utn.UTNPhones.domain.Call;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.services.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calls")
public class CallController {
    @Autowired
    CallService callService;

    @PostMapping("/")
    public Call create(@RequestBody Call call) throws ParamException {
        if(call.verifyNullValues()) throw new ParamException("Missing data");
        return this.callService.create(call);
    }
}
