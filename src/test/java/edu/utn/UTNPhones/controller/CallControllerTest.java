package edu.utn.UTNPhones.controller;

import edu.utn.UTNPhones.controllers.CallController;
import edu.utn.UTNPhones.projections.MinutesOfCallNewYear2001;
import edu.utn.UTNPhones.services.CallService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallControllerTest {
    @Autowired
    CallController callController;
    @Mock
    CallService callService;

    @Before
    public void setUp(){
        initMocks(this);
    }

    @Test
    public void getMinutesOfNewYear2001Test(){ //Habia que hacer la carga de todos los datos pero no me dio el tiempo
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        MinutesOfCallNewYear2001 minutesOfCallNewYear2001 = factory.createProjection(MinutesOfCallNewYear2001.class);
        minutesOfCallNewYear2001.setMinutesOfNewYear2001("02:50");

        when(callService.getMinutesOfNewYear2001()).thenReturn(minutesOfCallNewYear2001);

        MinutesOfCallNewYear2001 minutes = callController.getMinutesOfNewYear2001();

        assertEquals(minutesOfCallNewYear2001, minutes);
    }
}
