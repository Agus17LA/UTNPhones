package edu.utn.UTNPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Integer id;

    @ManyToOne(targetEntity = PhoneLine.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="number_line",nullable = false)
    private String numberLine;

    @Column(name="quantity_calls")

    private Integer quantityCalls;

    @Column(name="invoice_date")

    private LocalDateTime invoiceDate;

    @Column(name="invoice_expiry_date")

    private LocalDateTime invoiceExpiryDate;

    @Column(name="total_cost")

    private Float totalCost;

    @Column(name="total_price")

    private Float totalPrice;

    @Column(name="is_paid")

    private boolean isPaid;
}
