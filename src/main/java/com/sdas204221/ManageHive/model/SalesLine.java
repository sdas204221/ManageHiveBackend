package com.sdas204221.ManageHive.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Sid;
    @ManyToOne
    private Invoice invoice;
    private String description;
    private double quantity;
    private double unitPrice;
    @Column(columnDefinition = "int default 0")
    private double tax;
}
