package edu.utn.UTNPhones.controllers.models;

import edu.utn.UTNPhones.domain.PhoneLine;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.services.PhoneLineService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@AllArgsConstructor
@Controller
public class PhoneLineController {
    @Autowired
    PhoneLineService phoneLineService;

    /* ------------------------------------------------------------------------------------------------------ */

    public PhoneLine create(PhoneLine phoneLine) {
        return this.phoneLineService.create(phoneLine);
    }

    public Boolean unsubscribePhoneLine(String numberLine) {
        return phoneLineService.unsubscribePhoneLine(numberLine).getLineStatus() == PhoneLine.LineStatus.INACTIVE;
    }

    public Boolean suspendPhoneLine(String numberLine) {
        return phoneLineService.suspendPhoneLine(numberLine).getLineStatus() == PhoneLine.LineStatus.SUSPENDED;
    }

    public List<PhoneLine> getLines(Optional<String> numberLine){
        return this.phoneLineService.getLines(numberLine);
    }

    public PhoneLine update(String numberLine, Map<String, Object> changes) throws ParamException {
        return this.phoneLineService.update(numberLine,changes);
    }

    public List<PhoneLine> getLineOfUser(String dni) {
        return phoneLineService.getLinesOfUser(dni);
    }
}
