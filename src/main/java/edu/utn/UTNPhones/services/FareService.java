package edu.utn.UTNPhones.services;

import edu.utn.UTNPhones.domain.City;
import edu.utn.UTNPhones.domain.Fare;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.repositories.ICityRepository;
import edu.utn.UTNPhones.repositories.IFareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class FareService {

    private IFareRepository fareRepository;
    private ICityRepository cityRepository;

    @Autowired
    public FareService(IFareRepository fareRepository, ICityRepository cityRepository) {
        this.fareRepository = fareRepository;
        this.cityRepository = cityRepository;
    }

    public List getFares(Optional<String> originCity,Optional<String> destinationCity) throws DataAccessException, NotExistException {
        if(originCity.isPresent() && destinationCity.isPresent()){
            City cityO = cityRepository.getByCityName(originCity.get());
            City cityD = cityRepository.getByCityName(destinationCity.get());
            if(cityO == null || cityD == null) throw new NotExistException("The specified city does not exist");
            return Collections.singletonList(this.fareRepository.findByOriginCityAndDestinationCity(cityO, cityD));
        }else if(originCity.isPresent()){
            City cityO = cityRepository.getByCityName(originCity.get());
            if(cityO == null) throw new NotExistException("The specified city does not exist");
            return this.fareRepository.findAllByOriginCity(cityO);
        }else{
            return this.fareRepository.findAll();
        }
    }


}
