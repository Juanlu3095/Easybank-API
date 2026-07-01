package com.jcooldevelopment.easybank_api.contracts.entity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jcooldevelopment.easybank_api.annotations.DniValidatorAnnotation;
import com.jcooldevelopment.easybank_api.contracts.enums.UserRole;

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
@Builder
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
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Phone cannot be blank.")
    @Column(name = "phone", unique = true, nullable = false, length = 45)
    private String phone;

    @NotBlank(message = "Usercode cannot be blank.")
    @Column(name = "usercode", nullable = false, unique = true)
    private String usercode; // Must generate a random number of a given length as username in login form

    @NotBlank(message = "Password cannot be blank.")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "pin", nullable = true)
    private String pin; // Secret code for safe transactions

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @ColumnDefault("'CLIENT'")
    private UserRole role;

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
}
