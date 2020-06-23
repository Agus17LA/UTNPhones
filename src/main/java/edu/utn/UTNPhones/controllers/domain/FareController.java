package edu.utn.UTNPhones.controllers.domain;

import edu.utn.UTNPhones.domain.Fare;
import edu.utn.UTNPhones.exceptions.EmptyListException;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.services.FareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class FareController {
    @Autowired
    private FareService fareService;

    public List<Fare> getFares(Optional<String> originCity, Optional<String> destinationCity) throws NotExistException {
        return this.fareService.getFares(originCity,destinationCity);
    }

}
