package edu.utn.UTNPhones.services;

import edu.utn.UTNPhones.domain.Call;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.dtos.NewCallDto;
import edu.utn.UTNPhones.exceptions.ValidationException;
import edu.utn.UTNPhones.projections.CallOfUser;
import edu.utn.UTNPhones.projections.MinutesOfCallNewYear2001;
import edu.utn.UTNPhones.projections.TopTenDestinationsByUser;
import edu.utn.UTNPhones.repositories.ICallRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@AllArgsConstructor
@Service
public class CallService {

    @Autowired
    ICallRepository callRepository;

    public Call create(NewCallDto newCall) throws DataAccessException, ValidationException {
        if(newCall.getDestinationNumber() == newCall.getOriginNumber()) throw new ValidationException("The lines cannot be the same");
        Call call = new Call(newCall.getOriginNumber(),newCall.getDestinationNumber(),newCall.getDuration(),newCall.getDateTime());
        return this.callRepository.save(call);
    }

    public List<CallOfUser> getCallOfUser(String dni) {
        return this.callRepository.getCallsOfUser(dni);
    }

    public List<CallOfUser> getCallsByDates(LocalDateTime firstDate, LocalDateTime secondDate, User loggedUser) throws ValidationException {
        if( secondDate.isBefore(firstDate) ) throw new ValidationException("The second date must be greater than the first");
        return this.callRepository.getCallsByDates(firstDate.toString(),secondDate.toString(),loggedUser.getIdCard());
    }

    public List<TopTenDestinationsByUser> getTopTenDestinations(User loggedUser) {
        return this.callRepository.getTopTenDestinationsByUser(loggedUser.getIdCard());
    }
}
