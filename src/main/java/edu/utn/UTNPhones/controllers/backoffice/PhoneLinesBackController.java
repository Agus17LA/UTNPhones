package edu.utn.UTNPhones.controllers.backoffice;

import edu.utn.UTNPhones.controllers.PhoneLineController;
import edu.utn.UTNPhones.domain.PhoneLine;
import edu.utn.UTNPhones.exceptions.ParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/backoffice/lines")
public class PhoneLinesBackController {
    @Autowired
    PhoneLineController phoneLineController;

    @PostMapping("/")
    public ResponseEntity addPhoneLine(@RequestBody PhoneLine line) throws ParamException {
        if(line.verifyNullValues()) throw new ParamException("Phone-line attributes cannot be null");
        PhoneLine newLine = phoneLineController.create(line);
        return ResponseEntity.created(URI.create("http://localhost:8080/backoffice/lines/" + newLine.getId())).body(newLine);
    }

    @DeleteMapping("/{numberLine}")
    public ResponseEntity unsubscribePhoneLine(@PathVariable String numberLine) throws ParamException {
        if(numberLine == null) throw new ParamException("You must specify an number-line");
        return ResponseEntity.ok().body(phoneLineController.unsubscribePhoneLine(numberLine));
    }

    @PutMapping("/{numberLine}")
    public ResponseEntity suspendPhoneLine(@PathVariable String numberLine) throws ParamException{
        if(numberLine == null) throw new ParamException("You must specify an number-line");
        return ResponseEntity.ok().body(phoneLineController.suspendPhoneLine(numberLine));
    }

    @PatchMapping("/{numberLine}")
    public ResponseEntity modifyPhoneLine(@PathVariable String numberLine, @RequestBody Map<String,Object> changes) throws ParamException {
        if(numberLine == null) throw new ParamException("You must specify an number-line");
        return ResponseEntity.ok().body(phoneLineController.update(numberLine,changes));
    }

    @GetMapping(value = {"/","/{numberLine}"})
    public ResponseEntity getPhoneLines(@PathVariable(required = false) Optional<String> numberLine){
        List lines = phoneLineController.getLines(numberLine);
        if(lines.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok().body(lines);
    }

    @GetMapping("/user/{dni}")
    public ResponseEntity getPhoneLinesOfUser(@PathVariable String dni) throws ParamException {
        if (dni == null) throw new ParamException("You must specify an dni");
        List phoneLines = phoneLineController.getLineOfUser(dni);
        if(phoneLines.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok().body(phoneLines);
    }


}
