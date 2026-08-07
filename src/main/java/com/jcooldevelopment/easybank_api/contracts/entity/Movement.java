package com.jcooldevelopment.easybank_api.contracts.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movement {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    // Refers to the one in which amount is updated
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = true)
    private Account account;

    @Column(name = "external_account", nullable = true)
    private String externalAccount;

    @ManyToOne
    @JoinColumn(name = "operation_id", nullable = false)
    private Operation operation;

    @Digits(integer = 17, fraction = 2)
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false, updatable = true) // updatable true for updating in put requests
    private LocalDateTime updatedAt;
}
