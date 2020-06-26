package edu.utn.UTNPhones.controller.models;
import edu.utn.UTNPhones.controllers.models.FareController;
import edu.utn.UTNPhones.domain.Fare;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.services.FareService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
public class FareControllerTest {
    private FareController fareController;
    private Fare fare;
    @Mock
    private FareService fareService;

    @Before
    public void setUp(){
        initMocks(this);
        this.fareController = new FareController(fareService);
        this.fare = new Fare(1,null,null,(float)1.5,(float)2.5);
    }

    @Test
    public void getFaresTest() throws NotExistException {
        List<Fare> list = Collections.singletonList(this.fare);
        when(fareService.getFares(null,null)).thenReturn(list);
        List<Fare> listResponse = fareController.getFares(null,null);
        assertEquals(listResponse,list);
        verify(fareService,times(1)).getFares(null,null);
    }
}
