package edu.utn.UTNPhones.services;

import edu.utn.UTNPhones.domain.City;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.repositories.ICityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired
    private ICityRepository cityRepository;

    public City create(City city) throws DataAccessException {
        return this.cityRepository.save(city);
    }

    public List<City> getAll() {
        return this.cityRepository.findAll();
    }

    public void update(City city) throws DataAccessException, NotExistException {

    }

    public void delete(Integer i) throws DataAccessException, NotExistException {

    }

    public void logicDelete(Integer i) throws DataAccessException, NotExistException {

    }

    public void test(City city){

    }

}
