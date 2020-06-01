package edu.utn.UTNPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="calls")
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="origin_line_id")
    private PhoneLine originPhone;

    @Column(name="origin_number_line", nullable = false)
    private String originNumberLine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="destination_line_id")
    private PhoneLine destinationPhone;

    @Column(name="destination_number_line", nullable = false)
    private String destinationNumberLine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Column(name="duration", nullable = false)
    private Integer duration;

    @Column(name="date_time", nullable = false)
    private Date callDate;

    @Column(name="total_cost")
    private Float totalCost;

    @Column(name="total_price")
    private Float totalPrice;
}
