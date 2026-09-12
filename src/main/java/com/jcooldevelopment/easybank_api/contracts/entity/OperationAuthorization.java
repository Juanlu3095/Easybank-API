package com.jcooldevelopment.easybank_api.contracts.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@AllArgsConstructor 
@NoArgsConstructor
@Table(name = "OperationAuthorization")
public class OperationAuthorization {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @OneToOne
    @Column(name = "operation_id", nullable = false)
    private Operation operation;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
