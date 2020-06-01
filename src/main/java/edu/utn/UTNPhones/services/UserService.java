package edu.utn.UTNPhones.services;


import edu.utn.UTNPhones.domain.User;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.repositories.IUserRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository){
        this.userRepository = userRepository;
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

    public void update(User userUpdated) throws DataAccessException, NotExistException {
        User userToUpdate = this.userRepository.getOne(userUpdated.getId());
        if(userToUpdate != null) this.userRepository.save(userUpdated);
        else throw new NotExistException("User not exists");
    }

    public void delete(Integer userId) throws DataAccessException,NotExistException {
        User user = userRepository.getOne(userId);
        if(user!=null)
            userRepository.delete(user);
        else
            throw new NotExistException("User not exists");
    }

    public void logicDelete(Integer userId) throws NotExistException {
        User user = userRepository.getOne(userId);
        if(user != null){
            if (user.isUserStatus()) {
                user.setUserStatus(false);
            } else {
                user.setUserStatus(true);
            }
        }else{
            throw new NotExistException("User not exists");
        }
    }

}
