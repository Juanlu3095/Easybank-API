package com.jcooldevelopment.easybank_api.contracts.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue
    @Column(name="id")
    private UUID id;

    @NotBlank(message="Name cannot be blank.")
    @Column(name="name", nullable=false, length=50)
    private String name;

    @NotBlank(message="Surname cannot be blank.")
    @Column(name="surname", nullable=false, length=100)
    private String surname;

    @Email(regexp="^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message="Email format is not correct.")
    @Column(name="email", nullable=false, length=50)
    private String email;

    @NotBlank(message="Phone cannot be blank.")
    @Column(name="phone", nullable=false, length=50)
    private String phone;

    @NotBlank(message="Message cannot be blank.")
    @Column(name="message", nullable=false)
    private String message;

    // Datetime
    // columnDefinition is the literal definition of the row, you cannot ignore "TIMESTAMP". This is necessary for DB to use default now()
    // This column can be null because is the BD who generates the timestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
