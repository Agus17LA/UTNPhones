package edu.utn.UTNPhones.services;

import edu.utn.UTNPhones.domain.Call;
import edu.utn.UTNPhones.projections.MinutesOfCallNewYear2001;
import edu.utn.UTNPhones.repositories.ICallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CallService {

    @Autowired
    ICallRepository callRepository;

    public Call create(Call newCall) throws DataAccessException {
        return this.callRepository.save(newCall);
    }

    public MinutesOfCallNewYear2001 getMinutesOfNewYear2001() throws DataAccessException{
        return this.callRepository.getMinutesOfNewYear2001();
    }
}
