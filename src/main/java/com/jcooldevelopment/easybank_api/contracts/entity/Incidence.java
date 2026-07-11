package com.jcooldevelopment.easybank_api.contracts.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;

import com.jcooldevelopment.easybank_api.contracts.enums.IncidenceStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Incidences")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Incidence {
    @Id
    @GeneratedValue
    @Column(name="id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false) // Later must be notNullable
    private User user_id;

    @ManyToOne
    @JoinColumn(name = "incidence_type", nullable = false)
    private IncidenceType incidence_type;

    @NotBlank(message="Message cannot be blank.")
    @Column(name="message", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @ColumnDefault("'IN_PROCESS'")
    private IncidenceStatus status;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false, updatable = true) // updatable true for updating in put requests
    private LocalDateTime updatedAt;
}
