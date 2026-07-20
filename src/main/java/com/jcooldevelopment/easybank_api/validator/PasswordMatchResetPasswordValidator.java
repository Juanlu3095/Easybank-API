package com.jcooldevelopment.easybank_api.validator;

import com.jcooldevelopment.easybank_api.annotations.PasswordMatchAnnotation;
import com.jcooldevelopment.easybank_api.dto.Auth.PasswordRepeatDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchResetPasswordValidator implements ConstraintValidator<PasswordMatchAnnotation, PasswordRepeatDto>{

    @Override
    public boolean isValid(PasswordRepeatDto dto, ConstraintValidatorContext context) {
        var password = dto.getPassword();
        var repeatPassword = dto.getRepeatPassword();

        if (password == null || repeatPassword == null) {
            return false;
        }

        if (password.equals(repeatPassword)) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Fields password and repeat password do not match.")
            .addPropertyNode("repeatPassword")
            .addConstraintViolation();

        return false;
    }
}
