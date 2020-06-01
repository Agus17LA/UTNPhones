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

    public void update(Fare fare) throws DataAccessException, NotExistException {
        Fare fareToUpdate = this.fareRepository.getOne(fare.getId());
        if(fareToUpdate!=null){
            fareToUpdate.setCostPerMinute(fare.getCostPerMinute());
            fareToUpdate.setDestinationCity(fare.getDestinationCity());
            fareToUpdate.setPricePerMinute(fare.getPricePerMinute());
            fareToUpdate.setOriginCity(fare.getOriginCity());
            this.fareRepository.save(fareToUpdate);
        }else{
            throw new NotExistException("Fare not exist");
        }
    }

}
