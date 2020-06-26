package edu.utn.UTNPhones.controllers.models;

import edu.utn.UTNPhones.domain.Fare;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.services.FareService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Controller
public class FareController {
    @Autowired
    private FareService fareService;

    public List<Fare> getFares(Optional<String> originCity, Optional<String> destinationCity) throws NotExistException {
        return this.fareService.getFares(originCity,destinationCity);
    }

}
