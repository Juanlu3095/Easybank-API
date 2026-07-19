package com.jcooldevelopment.easybank_api.dto.Auth;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordDto {

    @Email(message = "Email does not have the correct format.")
    private String email;
}
