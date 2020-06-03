package edu.utn.UTNPhones.controllers;

import edu.utn.UTNPhones.domain.PhoneLine;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.services.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lines")
public class PhoneLineController {
    @Autowired
    PhoneLineService phoneLineService;

    @PostMapping("/")
    public PhoneLine create(@RequestBody PhoneLine phoneLine) throws ParamException {
        if(phoneLine.verifyNullValues()) throw new ParamException("Phone line attributes cannot be null");
        return this.phoneLineService.create(phoneLine);
    }

    @GetMapping("/")
    public List<PhoneLine> getLines(@RequestParam(required = false) String numberLine){
        return this.phoneLineService.getLines(numberLine);
    }

    @PutMapping("/")
    public PhoneLine update(@RequestBody PhoneLine phoneLine) throws ParamException, NotExistException {
        if(phoneLine.verifyNullValues()) throw new ParamException("Phone line attributes cannot be null");
        return this.phoneLineService.update(phoneLine);
    }

}
