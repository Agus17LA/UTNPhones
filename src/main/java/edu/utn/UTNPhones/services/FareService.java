package edu.utn.UTNPhones.services;

import edu.utn.UTNPhones.domain.Fare;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.repositories.IFareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class FareService {
    @Autowired
    private IFareRepository fareRepository;

    public Fare create(Fare fare) throws DataAccessException {
        return this.fareRepository.save(fare);
    }

    public List getFares(Integer fareId) throws DataAccessException {
        return fareId != null ? Collections.singletonList(this.fareRepository.findById(fareId).orElseThrow()) : this.fareRepository.findAll();
    }

    public void update(Fare fareUpdated) throws DataAccessException, NotExistException {
        Fare fareToUpdate = this.fareRepository.getOne(fareUpdated.getId());
        if(fareToUpdate == null) throw new NotExistException("That fare doesn't exists");
        this.fareRepository.save(fareUpdated);
    }

}
