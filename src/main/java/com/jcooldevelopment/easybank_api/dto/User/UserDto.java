package com.jcooldevelopment.easybank_api.dto.User;

import java.util.UUID;

import com.jcooldevelopment.easybank_api.contracts.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private UUID id;

    @NotBlank(message = "Name cannot be blank.")
    private String name;
    
    @NotBlank(message = "Surname cannot be blank.")
    private String surname;

    @NotBlank(message = "DNI cannot be blank.")
    private String dni;

    @Email(message = "Email format is not valid.")
    private String email;

    @NotBlank(message = "Phone cannot be blank.")
    private String phone;

    @NotBlank(message = "Username cannot be blank.")
    private String username;

    private UserRole role;
}
