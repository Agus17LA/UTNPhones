package edu.utn.UTNPhones.service;
import edu.utn.UTNPhones.domain.City;
import edu.utn.UTNPhones.domain.Fare;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.repositories.ICityRepository;
import edu.utn.UTNPhones.repositories.IFareRepository;
import edu.utn.UTNPhones.services.FareService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
public class FareServiceTest {
    private FareService fareService;
    private Fare fare;
    @Mock
    private IFareRepository fareRepository;
    @Mock
    private ICityRepository cityRepository;

    @Before
    public void setUp(){
        initMocks(this);
        this.fareService = new FareService(fareRepository,cityRepository);
        this.fare = new Fare(1,new City(1,null,"Necochea",2262),new City(2,null,"Mar del Plata",223),(float)2.5,(float)5);
    }

    @After
    public void validate() {
        validateMockitoUsage(); //line YY
    }

    @Test
    public void getFaresTestBad(){
        when(cityRepository.getByCityName("abc")).thenReturn(null);
        when(cityRepository.getByCityName("def")).thenReturn(null);
        assertThrows(NotExistException.class, ()-> {
            fareService.getFares(Optional.of("asd"),Optional.of("def"));
        });
    }

    @Test
    public void getFaresTestBad2(){
        when(cityRepository.getByCityName("abc")).thenReturn(null);
        assertThrows(NotExistException.class, ()-> {
            fareService.getFares(Optional.of("asd"),Optional.empty());
        });
    }

    @Test
    public void getFareOriginAndDestinationTest() throws NotExistException {
        City origin = new City(1,null,"Necochea",2262);
        City destination = new City(2,null,"Mar del Plata",223);
        when(cityRepository.getByCityName("abc")).thenReturn(origin);
        when(cityRepository.getByCityName("def")).thenReturn(destination);
        when(fareRepository.findByOriginCityAndDestinationCity(origin,destination)).thenReturn(this.fare);
        List responseList = fareService.getFares(Optional.of("abc"),Optional.of("def"));
        assertEquals(responseList,List.of(this.fare));
    }

    @Test
    public void getFaresByOriginTest() throws NotExistException {
        City origin = new City(1,null,"Necochea",2262);
        when(cityRepository.getByCityName("abc")).thenReturn(origin);
        when(fareRepository.findAllByOriginCity(origin)).thenReturn(List.of(this.fare));
        List responseList = fareService.getFares(Optional.of("abc"),Optional.empty());
        assertEquals(responseList,List.of(this.fare));
    }

    @Test
    public void getAllFares() throws NotExistException {
        when(fareRepository.findAll()).thenReturn(List.of(this.fare));
        List responseList = fareService.getFares(Optional.empty(),Optional.empty());
        assertEquals(responseList,List.of(this.fare));
    }

}
