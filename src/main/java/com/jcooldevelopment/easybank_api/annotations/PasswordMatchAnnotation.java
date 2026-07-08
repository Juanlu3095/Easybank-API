package com.jcooldevelopment.easybank_api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jcooldevelopment.easybank_api.validator.PasswordMatchAuthValidator;
import com.jcooldevelopment.easybank_api.validator.PasswordMatchValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;  

@Target({
    ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { PasswordMatchValidator.class, PasswordMatchAuthValidator.class })
public @interface PasswordMatchAnnotation {
    String message() default "Fields password and repeat password do not match.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}