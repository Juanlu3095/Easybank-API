package com.jcooldevelopment.easybank_api.contracts.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ResetPasswordToken")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordToken {

    @Id
    @GeneratedValue
    @Column(name="id")
    private UUID id;

    @NotBlank(message = "Code cannot be blank.")
    @Column(name = "token")
    private String token;

    @OneToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name = "expires_at", columnDefinition = "TIMESTAMP DEFAULT NOW() + INTERVAL '1 hour'", insertable = false, updatable = false)
    private LocalDateTime expires_at;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false, updatable = false)
    private LocalDateTime created_at;

}
