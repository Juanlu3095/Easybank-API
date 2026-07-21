package com.jcooldevelopment.easybank_api.dto.Auth;

import org.hibernate.validator.constraints.Length;

import com.jcooldevelopment.easybank_api.annotations.DniValidatorAnnotation;
import com.jcooldevelopment.easybank_api.annotations.PasswordMatchAnnotation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * To create User without credentials
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatchAnnotation(message = "Fields password and repeat password do not match.")
public class RegisterDto extends PasswordRepeatDto{

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
}
