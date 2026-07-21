package com.jcooldevelopment.easybank_api.dto.Auth;

import org.hibernate.validator.constraints.Length;

import com.jcooldevelopment.easybank_api.annotations.PasswordMatchAnnotation;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatchAnnotation(message = "Fields password and repeat password do not match.")
public class ChangePasswordDto extends PasswordRepeatDto{

    @NotBlank(message = "Old password cannot be blank.")
    @Length(min = 8, max = 100, message = "Old password length must have a minimum of {min} characters and a maximum of {max}.")
    private String oldPassword;
}
