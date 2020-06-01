package edu.utn.UTNPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "id_card", nullable = false, unique = true)
    private String idCard;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name="user_surname", nullable = false)
    private String surname;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name="user_password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="user_type", nullable = false)
    private UserType userType;

    @Column(name="user_status")
    private boolean userStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="city_id")
    private City city;

    public enum UserType{
        EMPLOYEE, CLIENT, SYS
    }

    public boolean verifyNullValues(){
        return Stream.of(idCard, name, surname, username, password, userType, city).anyMatch(x -> x == null);
    }

}
