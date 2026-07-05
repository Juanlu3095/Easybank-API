package com.jcooldevelopment.easybank_api.dto.User;

import org.hibernate.validator.constraints.Length;

import com.jcooldevelopment.easybank_api.annotations.DniValidatorAnnotation;
import com.jcooldevelopment.easybank_api.annotations.EnumValidatorAnnotation;
import com.jcooldevelopment.easybank_api.annotations.PasswordMatchAnnotation;
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
@PasswordMatchAnnotation(message = "Fields password and repeat password do not match.") // https://stackoverflow.com/questions/65400172/validating-password-and-confirmed-password-spring-boot
public class CreateUserDto {

    @NotBlank(message = "Name cannot be blank.")
    @Length(min = 1, max = 50, message = "Name length must have a minimum of {min} characters and a maximum of {max}.")
    private String name;
    
    @NotBlank(message = "Surname cannot be blank.")
    @Length(min = 1, max = 100, message = "Surname length must have a minimum of {min} characters and a maximum of {max}.")
    private String surname;

    @NotBlank(message = "DNI cannot be blank.")
    @Length(min = 9, max = 9, message = "DNI must have a length of {min} characters.")
    @DniValidatorAnnotation
    private String dni;

    @Email(message = "Email format is not valid.")
    @Length(min = 1, max = 100, message = "Email length must have a minimum of {min} characters and a maximum of {max}.")
    private String email;

    @NotBlank(message = "Phone cannot be blank.")
    @Length(min = 1, max = 45, message = "Phone length must have a minimum of {min} characters and a maximum of {max}.")
    private String phone;

    @NotBlank(message = "Password cannot be blank.")
    @Length(min = 8, max = 100, message = "Password length must have a minimum of {min} characters and a maximum of {max}.")
    private String password;

    @NotBlank(message = "Repeat password cannot be blank.")
    @Length(min = 8, max = 100, message = "Password repeat length must have a minimum of {min} characters and a maximum of {max}.")
    private String repeatPassword;

    @EnumValidatorAnnotation(enumClass = UserRole.class, message = "Role is not a valid value.")
    private String role;
}
