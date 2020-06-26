package edu.utn.UTNPhones.controllers.sys;

import edu.utn.UTNPhones.controllers.models.CallController;
import edu.utn.UTNPhones.dtos.NewCallDto;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@AllArgsConstructor
@RestController
@RequestMapping("/SYS/calls")
public class CallSysController {
    @Autowired
    CallController callController;

    @PostMapping("/")
    public ResponseEntity addCall(@RequestBody NewCallDto newCall) throws ParamException, ValidationException {
        if(newCall.verifyNullValues()) throw new ParamException("Missing data");
        return ResponseEntity.ok().body(callController.create(newCall));
    }

}
