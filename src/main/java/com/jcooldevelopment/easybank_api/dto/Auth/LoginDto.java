package com.jcooldevelopment.easybank_api.dto.Auth;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto {

    @NotBlank(message = "Usercode cannot be blank.")
    private String usercode;

    @NotBlank(message = "Password cannot be blank.")
    @Length(min = 8, message = "Password length must have a minimum of {min} characters.")
    private String password;
}
