package com.jcooldevelopment.easybank_api.dto.User;

import org.hibernate.validator.constraints.Length;

import com.jcooldevelopment.easybank_api.annotations.DniValidatorAnnotation;
import com.jcooldevelopment.easybank_api.annotations.EnumValidatorAnnotation;
import com.jcooldevelopment.easybank_api.annotations.UserDniUniqueAnnotation;
import com.jcooldevelopment.easybank_api.annotations.UserEmailUniqueAnnotation;
import com.jcooldevelopment.easybank_api.contracts.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {

    @NotBlank(message = "Name cannot be blank.")
    @Length(min = 1, max = 50, message = "Name length must have a minimum of {min} characters and a maximum of {max}.")
    private String name;
    
    @NotBlank(message = "Surname cannot be blank.")
    @Length(min = 1, max = 100, message = "Surname length must have a minimum of {min} characters and a maximum of {max}.")
    private String surname;

    @NotBlank(message = "DNI cannot be blank.")
    @Length(min = 9, max = 9, message = "DNI must have a length {min} characters.")
    @DniValidatorAnnotation
    @UserDniUniqueAnnotation
    private String dni;

    @Email(message = "Email format is not valid.")
    @UserEmailUniqueAnnotation(message = "This email already exists.")
    @Length(min = 1, max = 100, message = "Email length must have a minimum of {min} characters and a maximum of {max}.")
    private String email;

    @NotBlank(message = "Phone cannot be blank.")
    @Length(min = 1, max = 45, message = "Phone length must have a minimum of {min} characters and a maximum of {max}.")
    private String phone;
    
    @NotBlank(message = "Pin cannot be blank.")
    private String pin;

    @NotBlank(message = "Password cannot be blank.")
    @Length(min = 8, max = 100, message = "Password length must have a minimum of {min} characters and a maximum of {max}.")
    private String password;

    @EnumValidatorAnnotation(enumClass = UserRole.class, message = "Role is not a valid value.")
    private String role;
}
