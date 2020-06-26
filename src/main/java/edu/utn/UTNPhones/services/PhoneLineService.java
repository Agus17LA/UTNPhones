package edu.utn.UTNPhones.services;

import edu.utn.UTNPhones.domain.PhoneLine;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.repositories.IPhoneLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PhoneLineService {
    IPhoneLineRepository phoneLineRepository;
    UserService userService;

    @Autowired
    public PhoneLineService(IPhoneLineRepository phoneLineRepository, UserService userService) {
        this.phoneLineRepository = phoneLineRepository;
        this.userService = userService;
    }

    public PhoneLine create(PhoneLine phoneLine) throws DataAccessException {
        return this.phoneLineRepository.save(phoneLine);
    }

    public PhoneLine unsubscribePhoneLine(String numberLine) throws DataAccessException {
        PhoneLine phoneLine = phoneLineRepository.getByNumberLine(numberLine).orElseThrow();
        phoneLine.setLineStatus(PhoneLine.LineStatus.INACTIVE);
        return phoneLineRepository.save(phoneLine);
    }

    public PhoneLine suspendPhoneLine(String numberLine) {
        PhoneLine phoneLine = phoneLineRepository.getByNumberLine(numberLine).orElseThrow();
        phoneLine.setLineStatus(PhoneLine.LineStatus.SUSPENDED);
        return phoneLineRepository.save(phoneLine);
    }

    public List<PhoneLine> getLines(Optional<String> numberLine) throws DataAccessException{
        return numberLine.isPresent() ? this.phoneLineRepository.findByNumberLine(numberLine.get()).orElseThrow() : this.phoneLineRepository.findAll();
    }

    public PhoneLine update(String numberLine, Map<String, Object> changes) throws DataAccessException, ParamException {
        PhoneLine phoneLine = phoneLineRepository.getByNumberLine(numberLine).orElseThrow();
        if(changes.containsKey("lineType") || changes.containsKey("lineStatus")) {
            changes.forEach(
                    (change, value) -> {
                        switch (change) {
                            case "lineType":
                                phoneLine.setLineType(PhoneLine.LineType.valueOf((String) value)); break;
                            case "lineStatus":
                                phoneLine.setLineStatus(PhoneLine.LineStatus.valueOf((String) value)); break;
                        }
                    }
            );
        } else throw new ParamException("You can only modify: 'lineType' - 'lineStatus'");
        return phoneLineRepository.save(phoneLine);
    }

    public List<PhoneLine> getLinesOfUser(String dni) throws DataAccessException {
        User user = (User) userService.getUsers(dni).get(0);
        return phoneLineRepository.findAllByUser(user);
    }
}
