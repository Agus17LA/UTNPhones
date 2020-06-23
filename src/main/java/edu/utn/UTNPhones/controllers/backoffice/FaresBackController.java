package edu.utn.UTNPhones.controllers.backoffice;

import edu.utn.UTNPhones.controllers.FareController;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.exceptions.ParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/backoffice/fares")
public class FaresBackController {
    @Autowired
    FareController fareController;

    @GetMapping("/")
    public ResponseEntity getFares(@RequestParam(required = false)Optional<String> originCity, @RequestParam(required = false) Optional<String> destinationCity) throws NotExistException {
        List fares = fareController.getFares(originCity,destinationCity);
        if(fares.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok().body(fares);
    }

}
