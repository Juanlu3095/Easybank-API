package com.jcooldevelopment.easybank_api.contracts.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jcooldevelopment.easybank_api.annotations.DniValidatorAnnotation;
import com.jcooldevelopment.easybank_api.contracts.enums.UserRole;
import com.jcooldevelopment.easybank_api.contracts.enums.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // Allows to create abstract methods to create objects of this class like interface for objects. It allows to implement Design patterns
@Table(name = "Users")
public class User implements UserDetails{

    @Id
    @GeneratedValue
    @Column(name="id")
    private UUID id;

    @NotBlank(message = "Name cannot be blank.")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotBlank(message = "Surname cannot be blank.")
    @Column(name = "surname", nullable = false, length = 100)
    private String surname;

    @DniValidatorAnnotation
    @Column(name = "dni", unique = true, nullable = false, length = 10)
    private String dni;

    @Email
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @NotBlank(message = "Phone cannot be blank.")
    @Column(name = "phone", unique = false, nullable = false, length = 45)
    private String phone;

    @NotBlank(message = "Usercode cannot be blank.")
    @Column(name = "usercode", nullable = false, unique = true)
    @Length(min = 10, max = 10, message = "Usercode length must have a minimum of {min} characters and a maximum of {max}.")
    private String usercode; // Must generate a random number of a given length as username in login form and must be UNIQUE

    @NotBlank(message = "Password cannot be blank.")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "pin", length = 50, nullable = true)
    private String pin; // Secret code for safe transactions

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 100, nullable = false)
    @ColumnDefault("'CLIENT'")
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    @ColumnDefault("'NOT_ENABLED'")
    private UserStatus status;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false, updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.usercode;
    }

    // This method is not needed probably since isEnabled also uses UserStatus
    @Override 
    public boolean isAccountNonLocked() { // MIN33:42
        return !this.status.equals(UserStatus.BLOCKED);
    }

    @Override
    public boolean isEnabled() {
        return this.status.equals(UserStatus.ENABLED);
    }
}
