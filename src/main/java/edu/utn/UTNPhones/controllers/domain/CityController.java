package edu.utn.UTNPhones.controllers.domain;

import edu.utn.UTNPhones.domain.City;
import edu.utn.UTNPhones.exceptions.EmptyListException;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CityController {
    @Autowired
    private CityService cityService;

    public void create(City city) throws ParamException {
        if(city.verifyNullValues()) throw new ParamException("Values of city cannot be null");
        this.cityService.create(city);
    }

    public List<City> getCities(Integer cityId) throws EmptyListException {
        List cities = this.cityService.getCities(cityId);
        if(cities.isEmpty()) throw new EmptyListException("Empty list of cities");
        return cities;
    }



}
