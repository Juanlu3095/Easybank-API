package com.jcooldevelopment.easybank_api.contracts.entity;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// It is needed to include an existing branch to IBAN or BIC/SWIFT
@Entity
@Table(name = "Branch")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental id
    @Column(name="id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Length(min = 4, max = 4, message = "IBAN branch's length must be 4.")
    @Column(name = "iban_code", nullable = false)
    private String iban_code; // The IBAN part which identifies the branch

    @Length(min = 4, max = 4, message = "BIC/SWIFT branch's length must be 4.")
    @Column(name = "bic_code", nullable = false)
    private String bic_code; // The BIC/SWIFT part which identifies the branch

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;
}
