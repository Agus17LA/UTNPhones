package edu.utn.UTNPhones.service;

import edu.utn.UTNPhones.repositories.ICallRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static org.mockito.MockitoAnnotations.initMocks;

public class CallServiceTest {
    @Mock
    ICallRepository callRepository;

    @Before
    public void setUp(){
        initMocks(this);
    }

    @Test
    public void testCreateCall(){

    }

}
