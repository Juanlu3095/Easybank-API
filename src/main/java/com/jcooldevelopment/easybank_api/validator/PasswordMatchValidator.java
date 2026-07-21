package com.jcooldevelopment.easybank_api.validator;

import com.jcooldevelopment.easybank_api.annotations.PasswordMatchAnnotation;
import com.jcooldevelopment.easybank_api.dto.Auth.PasswordRepeatDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatchAnnotation, PasswordRepeatDto> {

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

        // Since the annotation refers to the entire class, errors will affect the entire class not only the field.
        // Errors are global. With this we add the errors as field errors
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Fields password and repeat password do not match.")
            .addPropertyNode("repeatPassword")
            .addConstraintViolation();

        return false;
    }

}
