package edu.utn.UTNPhones.services;

import edu.utn.UTNPhones.domain.PhoneLine;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.repositories.IPhoneLineRepository;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PhoneLineService {
    @Autowired
    IPhoneLineRepository iPhoneLineRepository;

    public PhoneLine create(PhoneLine phoneLine) throws GenericJDBCException {
        return this.iPhoneLineRepository.save(phoneLine);
    }

    public PhoneLine update(PhoneLine phoneLineUpdated) throws DataAccessException, NotExistException {
        if( this.iPhoneLineRepository.getOne(phoneLineUpdated.getId()) == null ) throw new NotExistException("That line doesn't exists");
        return this.iPhoneLineRepository.save(phoneLineUpdated);
    }

    public List<PhoneLine> getLines(String numberLine) throws DataAccessException{
        return numberLine != null ? this.iPhoneLineRepository.findByNumberLine(numberLine).orElseThrow() : this.iPhoneLineRepository.findAll();
    }

}
