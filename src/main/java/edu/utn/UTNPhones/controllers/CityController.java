package edu.utn.UTNPhones.controllers;

import edu.utn.UTNPhones.domain.City;
import edu.utn.UTNPhones.exceptions.EmptyListException;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    @Autowired
    private CityService cityService;

    @PostMapping("/")
    public void create(@RequestBody City city) throws ParamException {
        if(city.verifyNullValues()) throw new ParamException("Values of city cannot be null");
        this.cityService.create(city);
    }

    @GetMapping("/")
    public List<City> allCities() throws EmptyListException {
        return this.cityService.getAll();
    }



}
