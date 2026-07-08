package com.jcooldevelopment.easybank_api.validator;

import com.jcooldevelopment.easybank_api.annotations.PasswordMatchAnnotation;
import com.jcooldevelopment.easybank_api.dto.Auth.RegisterDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchAuthValidator implements ConstraintValidator<PasswordMatchAnnotation, RegisterDto> {

    @Override
    public boolean isValid(RegisterDto registerDto, ConstraintValidatorContext context) {
        var password = registerDto.getPassword();
        var repeatPassword = registerDto.getRepeatPassword();

        if (password == null || repeatPassword == null) {
            return false;
        }

        if (password.equals(repeatPassword)) {
            return true;
        }

        // Since the annotation refers to the entire class, errors will affect the entire class not only the field.
        // Errors are global. With this we add the errors as field errors
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Fields password and repeat password do not match.")
            .addPropertyNode("repeatPassword")
            .addConstraintViolation();

        return false;
    }

}