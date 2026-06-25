package com.jcooldevelopment.easybank_api.contracts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="IncidenceType")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidenceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental id
    @Column(name = "id")
    private int Id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;
}
