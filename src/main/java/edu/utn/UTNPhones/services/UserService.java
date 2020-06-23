package edu.utn.UTNPhones.services;


import edu.utn.UTNPhones.domain.City;
import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class UserService {
    private final IUserRepository userRepository;
    private final CityService cityService;

    @Autowired
    public UserService(IUserRepository userRepository, CityService cityService){
        this.userRepository = userRepository;
        this.cityService = cityService;
    }

    public User login(String username, String password) throws NotExistException {
        User user = userRepository.getByUsernameAndPassword(username,password);
        return Optional.ofNullable(user).orElseThrow(()-> new NotExistException("User not exists"));
    }

    /*                                 ------                                      */

    public User create(User user) throws DataAccessException {
        return this.userRepository.save(user);
    }

    public List getUsers(Integer userId) throws DataAccessException {
        return userId != null ? Collections.singletonList(this.userRepository.findById(userId).orElseThrow()) : this.userRepository.findAll();
    }

    public void delete(Integer userId) throws DataAccessException,NotExistException {
        User user = userRepository.getOne(userId);
        if(user!=null)
            userRepository.delete(user);
        else
            throw new NotExistException("User not exists");
    }
    /* ---------------------------------------------------------------------------------- */
    public User addClient(User client) throws DataAccessException{
        return userRepository.save(client);
    }

    public User changeStatus(String dni, boolean status) throws DataAccessException{
        User user = userRepository.getByIdCard(dni).orElseThrow();
        user.setUserStatus(status);
        return userRepository.save(user);
    }

    public User update(String dni, Map<String, Object> changes) throws DataAccessException{
        User user = userRepository.getByIdCard(dni).orElseThrow();
        changes.forEach(
                (change,value) ->  {
                    switch (change){
                        case "dni": user.setIdCard((String) value); break;
                        case "name": user.setName((String) value); break;
                        case "surname": user.setSurname((String) value); break;
                        case "username": user.setUsername((String) value); break;
                        case "password": user.setPassword((String) value); break;
                        case "userType": user.setUserType((User.UserType) value); break;
                        case "userStatus": user.setUserStatus((boolean) value); break;
                        case "city":
                            City city = (City) cityService.getCities((Integer) value).get(0);
                            user.setCity(city);
                            break;
                    }
                }
        );
        return userRepository.save(user);
    }

    public List getClients(Optional<String> clientDni) throws DataAccessException {
        return clientDni.isPresent() ? Collections.singletonList(this.userRepository.findByIdCardAndUserType(clientDni.get(), User.UserType.CLIENT).orElseThrow()) : this.userRepository.findAllByUserType(User.UserType.CLIENT);
    }


}
