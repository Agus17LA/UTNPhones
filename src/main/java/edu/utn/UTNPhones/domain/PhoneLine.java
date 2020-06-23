package edu.utn.UTNPhones.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.stream.Stream;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="phone_lines")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class PhoneLine{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name="number_line", nullable = false, unique = true)
    private String numberLine;

    @Enumerated(EnumType.STRING)
    @Column(name="line_type", nullable = false)
    private LineType lineType;

    @Enumerated(EnumType.STRING)
    @Column(name="line_status")
    private LineStatus lineStatus = LineStatus.ACTIVE;

    public enum LineType{
        LANDLINE,MOBILE
    }
    public enum LineStatus{
        ACTIVE,INACTIVE,SUSPENDED
    }



    public boolean verifyNullValues(){  return Stream.of(user,numberLine,lineType).anyMatch(x->x == null);   }

}
