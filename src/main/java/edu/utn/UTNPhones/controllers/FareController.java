package edu.utn.UTNPhones.controllers;

import edu.utn.UTNPhones.domain.Fare;
import edu.utn.UTNPhones.exceptions.EmptyListException;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.services.FareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fares")
public class FareController {
    @Autowired
    private FareService fareService;

    @PostMapping("/")
    public Fare create(@RequestBody Fare fare) throws ParamException {
        if(fare.verifyNullValues()) throw new ParamException("Values of fare cannot be null");
        return this.fareService.create(fare);
    }

    @PutMapping("/")
    public void update(@RequestBody Fare fare) throws ParamException, NotExistException {
        if(fare.verifyNullValues()) throw new ParamException("Values of fare cannot be null");
        fareService.update(fare);
    }

    @GetMapping("/")
    public List<Fare> getFares(@RequestParam(required = false) Integer fareId) throws EmptyListException {
        List fares = this.fareService.getFares(fareId);
        if(fares.isEmpty()) throw new EmptyListException("Empty list");
        return fares;
    }

}
