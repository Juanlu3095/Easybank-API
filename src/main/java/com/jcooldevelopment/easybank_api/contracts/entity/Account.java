package com.jcooldevelopment.easybank_api.contracts.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;

import com.jcooldevelopment.easybank_api.contracts.enums.AccountStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    @Column(name="id")
    private UUID id;

    // https://www.linqz.io/2019/02/choosing-data-type-for-monetary-calculation-in-java-float-double-or-bigdecimal.html
    // BigDecimal has more precision than float or double. If float is used, 0.1 + 0.2 = 0.30000000000000004
    // It is an only-read field.
    @Digits(integer = 17, fraction = 2)
    @Column(name = "balance")
    private BigDecimal balance;

    // https://www.bbva.com/es/salud-financiera/swift-e-iban/
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @ColumnDefault("'NOT_ACTIVATED'")
    private AccountStatus status;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="accountType", nullable = false)
    private AccountType account_type;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false, updatable = false)
    private LocalDateTime created_at;
}
