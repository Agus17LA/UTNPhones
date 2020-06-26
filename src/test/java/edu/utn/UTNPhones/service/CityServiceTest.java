package edu.utn.UTNPhones.service;
import java.util.*;

import edu.utn.UTNPhones.domain.City;
import edu.utn.UTNPhones.repositories.ICityRepository;
import edu.utn.UTNPhones.services.CityService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
public class CityServiceTest {
    private CityService cityService;
    @Mock
    private ICityRepository cityRepository;

    @Before
    public void setUp(){
        initMocks(this);
        this.cityService = new CityService(cityRepository);
    }

    @Test
    public void getCitiesTest(){
        List list = List.of(new City(1,null,"Necochea",2262));
        when(cityRepository.findAll()).thenReturn(list);
        List responseList = this.cityService.getCities(null);
        assertEquals(responseList,list);
    }

    @Test
    public void getCityTest(){
        Optional<City> city = Optional.of(new City(1, null, "Necochea", 2262));
        when(cityRepository.findById(1)).thenReturn(city);
        List responseList = this.cityService.getCities(1);
        assertEquals(responseList,List.of(city.get()));
    }

}
