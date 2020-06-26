package edu.utn.UTNPhones.service;
import edu.utn.UTNPhones.domain.City;
import edu.utn.UTNPhones.domain.PhoneLine;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.repositories.IPhoneLineRepository;
import edu.utn.UTNPhones.services.PhoneLineService;
import edu.utn.UTNPhones.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
public class PhoneLineServiceTest {
    private PhoneLineService phoneLineService;
    private PhoneLine phoneLine;
    @Mock
    private IPhoneLineRepository phoneLineRepository;
    @Mock
    private UserService userService;

    @Before
    public void setUp(){
        initMocks(this);
        this.phoneLineService = new PhoneLineService(phoneLineRepository,userService);
        phoneLine = new PhoneLine(1,null,"2262677713", PhoneLine.LineType.MOBILE, PhoneLine.LineStatus.ACTIVE);
    }

    @Test
    public void createTest(){
        when(phoneLineRepository.save(new PhoneLine())).thenReturn(this.phoneLine);
        PhoneLine responsePhoneLine = phoneLineService.create(new PhoneLine());
        assertEquals(responsePhoneLine,this.phoneLine);
        verify(phoneLineRepository,times(1)).save(new PhoneLine());
    }

    @Test
    public void unsubscribePhoneLineTest(){
        when(phoneLineRepository.getByNumberLine("2262677713")).thenReturn(java.util.Optional.ofNullable(this.phoneLine));
        when(phoneLineRepository.save(this.phoneLine)).thenReturn(this.phoneLine);
        PhoneLine responsePhoneLine = phoneLineService.unsubscribePhoneLine("2262677713");
        assertEquals(responsePhoneLine,this.phoneLine);
        verify(phoneLineRepository,times(1)).getByNumberLine("2262677713");
        verify(phoneLineRepository,times(1)).save(this.phoneLine);
    }

    @Test
    public void suspendPhoneLineTest(){
        when(phoneLineRepository.getByNumberLine("2262677713")).thenReturn(java.util.Optional.ofNullable(this.phoneLine));
        when(phoneLineRepository.save(this.phoneLine)).thenReturn(this.phoneLine);
        PhoneLine responsePhoneLine = phoneLineService.suspendPhoneLine("2262677713");
        assertEquals(responsePhoneLine,this.phoneLine);
        verify(phoneLineRepository,times(1)).getByNumberLine("2262677713");
        verify(phoneLineRepository,times(1)).save(this.phoneLine);
    }

    @Test
    public void getLineTest(){
        List<PhoneLine> phoneLine = Collections.singletonList(this.phoneLine);
        when(phoneLineRepository.findByNumberLine("2262677713")).thenReturn(Optional.of(phoneLine));
        List responsePhoneLine = this.phoneLineService.getLines(Optional.of("2262677713"));
        assertEquals(responsePhoneLine.get(0),phoneLine.get(0));
        verify(phoneLineRepository,times(1)).findByNumberLine("2262677713");
    }

    @Test
    public void getLinesTest(){
        List<PhoneLine> phoneLineList = Collections.singletonList(this.phoneLine);
        when(phoneLineRepository.findAll()).thenReturn(phoneLineList);
        List responsePhoneLineList = phoneLineService.getLines(java.util.Optional.empty());
        assertEquals(responsePhoneLineList,phoneLineList);
        verify(phoneLineRepository,times(1)).findAll();
    }

    @Test
    public void getLinesOfUser(){
        when(userService.getUsers("41923121")).thenReturn(List.of(new User()));
        when(phoneLineRepository.findAllByUser(new User())).thenReturn(List.of(this.phoneLine));
        List responsePhoneLine = phoneLineService.getLinesOfUser("41923121");
        assertEquals(responsePhoneLine,List.of(this.phoneLine));
    }

    @Test
    public void updateMapTest() throws ParamException {
        Map<String, Object> map = new HashMap<>();
        map.put("lineType", "MOBILE");
        map.put("lineStatus", "ACTIVE");

        Map<String, Object> expected = new HashMap<>();
        expected.put("lineType", "MOBILE");
        expected.put("lineStatus", "ACTIVE");

        when(phoneLineRepository.getByNumberLine("2262677713")).thenReturn(Optional.ofNullable(this.phoneLine));
        when(phoneLineRepository.save(this.phoneLine)).thenReturn(this.phoneLine);
        PhoneLine responsePhoneLine = phoneLineService.update("2262677713", map);
        assertEquals(responsePhoneLine,this.phoneLine);
        assertThat(map, is(expected));
    }

    @Test
    public void updateMapTest2() throws ParamException {
        Map<String, Object> map = new HashMap<>();
        map.put("lineType", "MOBILE");

        Map<String, Object> expected = new HashMap<>();
        expected.put("lineType", "MOBILE");

        when(phoneLineRepository.getByNumberLine("2262677713")).thenReturn(Optional.ofNullable(this.phoneLine));
        when(phoneLineRepository.save(this.phoneLine)).thenReturn(this.phoneLine);
        PhoneLine responsePhoneLine = phoneLineService.update("2262677713", map);
        assertEquals(responsePhoneLine,this.phoneLine);
        assertThat(map, is(expected));
    }

    @Test
    public void updateMapTest3() throws ParamException {
        Map<String, Object> map = new HashMap<>();

        map.put("lineStatus", "ACTIVE");
        Map<String, Object> expected = new HashMap<>();

        expected.put("lineStatus", "ACTIVE");
        when(phoneLineRepository.getByNumberLine("2262677713")).thenReturn(Optional.ofNullable(this.phoneLine));
        when(phoneLineRepository.save(this.phoneLine)).thenReturn(this.phoneLine);
        PhoneLine responsePhoneLine = phoneLineService.update("2262677713", map);
        assertEquals(this.phoneLine,responsePhoneLine);
        assertThat(map, is(expected));
    }




}
