package com.jcooldevelopment.easybank_api.contracts.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="AccountType")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountType {

    @Id
    @GeneratedValue
    @Column(name="id")
    private UUID id;

    @NotBlank(message = "Name cannot be blank.")
    @Column(name = "name")
    private String name;

    @Column(name = "terms", columnDefinition = "TEXT")
    private String terms;
}
