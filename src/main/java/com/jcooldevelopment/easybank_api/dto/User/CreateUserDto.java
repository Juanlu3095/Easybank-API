package com.jcooldevelopment.easybank_api.dto.User;

import com.jcooldevelopment.easybank_api.annotations.DniValidatorAnnotation;
import com.jcooldevelopment.easybank_api.annotations.EnumValidatorAnnotation;
import com.jcooldevelopment.easybank_api.contracts.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * To create User with ADMIN role
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {

    @NotBlank(message = "Name cannot be blank.")
    private String name;
    
    @NotBlank(message = "Surname cannot be blank.")
    private String surname;

    @NotBlank(message = "DNI cannot be blank.")
    @DniValidatorAnnotation
    private String dni;

    @Email(message = "Email format is not valid.")
    private String email;

    @NotBlank(message = "Phone cannot be blank.")
    private String phone;

    @NotBlank(message = "Password cannot be blank.")
    private String password;

    @NotBlank(message = "Repeat password cannot be blank.")
    private String repeatPassword;

    @EnumValidatorAnnotation(enumClass = UserRole.class, message = "Role is not a valid value.")
    private UserRole role;
}
