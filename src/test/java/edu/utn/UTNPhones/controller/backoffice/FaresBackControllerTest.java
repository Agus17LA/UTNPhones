package edu.utn.UTNPhones.controller.backoffice;
import edu.utn.UTNPhones.controllers.backoffice.FaresBackController;
import edu.utn.UTNPhones.controllers.models.FareController;
import edu.utn.UTNPhones.domain.City;
import edu.utn.UTNPhones.domain.Fare;
import edu.utn.UTNPhones.exceptions.NotExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
public class FaresBackControllerTest {
    private FaresBackController faresBackController;
    @Mock
    private FareController fareController;

    @Before
    public void setUp(){
        initMocks(this);
        this.faresBackController = new FaresBackController(fareController);
    }

    @Test
    public void getFaresTestEmpty() throws NotExistException {
        when(fareController.getFares(Optional.of("Necochea"), Optional.of("Mar del Plata"))).thenReturn(List.of());
        ResponseEntity responseEntity = this.faresBackController.getFares(Optional.of("Necochea"),Optional.of("Mar del Plata"));
        assertEquals(204,responseEntity.getStatusCodeValue());
    }

    @Test
    public void getFaresTest() throws NotExistException {
        List fares = Collections.singletonList(new Fare(1,new City(),new City(), (float) 2.5, (float) 5));
        when(fareController.getFares(Optional.of("Necochea"), Optional.of("Mar del Plata"))).thenReturn(fares);
        ResponseEntity responseEntity = this.faresBackController.getFares(Optional.of("Necochea"), Optional.of("Mar del Plata"));
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(fares,responseEntity.getBody());
    }
}
