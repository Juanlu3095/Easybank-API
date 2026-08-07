package com.jcooldevelopment.easybank_api.contracts.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @JoinColumn(name = "orderer_account_id", nullable = false)
    private Account ordererAccount;

    @ManyToOne
    @JoinColumn(name = "counterpart_account_id", nullable = true)
    private Account counterpartAccount;

    @Column(name = "counterpart_external_account_iban", nullable = true)
    private String counterpartExternalAccount;

    @Column(name = "concept", nullable = false)
    private String concept;

    @Column(name = "status", nullable = false)
    private OperationStatus status;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false, updatable = true) // updatable true for updating in put requests
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "operation")
    private List<Movement> movements;

    public void addMovement(Movement movement){
        if (this.movements == null) {
            this.movements = new ArrayList<>();
        }
        this.movements.add(movement);
    }
}
