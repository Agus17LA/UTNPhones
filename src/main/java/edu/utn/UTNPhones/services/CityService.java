package edu.utn.UTNPhones.services;

import edu.utn.UTNPhones.domain.City;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.repositories.ICityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CityService {
    @Autowired
    private ICityRepository cityRepository;

    public City create(City city) throws DataAccessException {
        return this.cityRepository.save(city);
    }

    public List getCities(Integer cityId) throws DataAccessException{
        return cityId != null ? Collections.singletonList(this.cityRepository.findById(cityId).orElseThrow()) : this.cityRepository.findAll();
    }
}
