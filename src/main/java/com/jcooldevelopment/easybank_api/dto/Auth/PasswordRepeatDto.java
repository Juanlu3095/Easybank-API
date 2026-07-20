package com.jcooldevelopment.easybank_api.dto.Auth;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRepeatDto {

    @NotBlank(message = "New password cannot be blank.")
    @Length(min = 8, max = 100, message = "New password length must have a minimum of {min} characters and a maximum of {max}.")
    String password;

    @NotBlank(message = "Repeat new password cannot be blank.")
    @Length(min = 8, max = 100, message = "New password repeat length must have a minimum of {min} characters and a maximum of {max}.")
    String repeatPassword;
}
