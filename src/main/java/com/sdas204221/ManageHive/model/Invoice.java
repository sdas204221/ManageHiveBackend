package com.sdas204221.ManageHive.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoiceNumber;
    @ManyToOne
    private User user;
    private Date issueDate;
    private String customerName;
    private String customerAddress;
    private double discount;
    private String paymentMethod;
    private String paymentTerms;
    private Date paymentDate;
    @Transient
    private List<SalesLine> salesLines;
    @Transient
    private double subtotal;
    @Transient
    private double totalAmount;

}
