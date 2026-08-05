package com.jcooldevelopment.easybank_api.contracts.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.jcooldevelopment.easybank_api.contracts.enums.OperationStatus;
import com.jcooldevelopment.easybank_api.contracts.enums.OperationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// An account operation has one or two movements.
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "type", nullable = false)
    private OperationType type;

    @ManyToOne
    @JoinColumn(name = "orderer_account_id")
    private Account ordererAccountId;

    @ManyToOne
    @JoinColumn(name = "counterpart_account_id")
    private Account counterpartAccountId;

    @Column(name = "concept")
    private String concept;

    @Column(name = "status")
    private OperationStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "operation")
    private List<Movement> movements;
}
