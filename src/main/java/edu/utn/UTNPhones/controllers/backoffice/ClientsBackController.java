package edu.utn.UTNPhones.controllers.backoffice;

import edu.utn.UTNPhones.controllers.UserController;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.exceptions.EmptyListException;
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
@RequestMapping("/backoffice/client")
public class ClientsBackController {
    @Autowired
    private UserController userController;

    @PostMapping("/")
    public ResponseEntity addClient(@RequestBody User client) throws ParamException {
        if(client.verifyNullValues()) throw new ParamException("User attributes cannot be null");
        client.setUserType(User.UserType.CLIENT);
        User newClient = this.userController.addClient(client);
        return ResponseEntity.created(URI.create("http://localhost:8080/backoffice/client/" + newClient.getId())).body(newClient);
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity logicDeleteClient(@PathVariable String dni) throws ParamException {
        if (dni == null) throw new ParamException("You must specify an dni");
        return ResponseEntity.ok().body(this.userController.changeStatus(dni, false));
    }

    @PatchMapping("/{dni}")
    public ResponseEntity modifyClient(@PathVariable String dni, @RequestBody Map<String,Object> changes) throws ParamException {
        if (dni == null) throw new ParamException("You must specify an dni");
        return ResponseEntity.ok().body(this.userController.update(dni,changes));
    }

    @GetMapping(value={"/{clientDni}","/"})
    public ResponseEntity getClients(@PathVariable(required=false) Optional<String> clientDni) throws EmptyListException {
        List users = this.userController.getClients(clientDni);
        if(users.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok().body(users);
    }

}
