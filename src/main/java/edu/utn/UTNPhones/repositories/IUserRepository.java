package edu.utn.UTNPhones.repositories;

import edu.utn.UTNPhones.domain.PhoneLine;
import edu.utn.UTNPhones.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Integer> {
    @Query("SELECT u FROM User u WHERE username = :username AND password = :password")
    User getByUsernameAndPassword(String username,String password);

    @Modifying
    @Query("UPDATE User u SET u.userStatus = false WHERE u.id = :userId")
    User logicDelete(Integer userId);

    Optional<User> getByIdCard(String dni);

    List<User> findAllByUserType(User.UserType userType);

    Optional<User> findByIdCardAndUserType(String clientDni, User.UserType client);
}
