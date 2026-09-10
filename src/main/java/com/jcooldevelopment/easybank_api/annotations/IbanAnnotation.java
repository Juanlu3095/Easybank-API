package com.jcooldevelopment.easybank_api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jcooldevelopment.easybank_api.validator.IbanValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({
    ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IbanValidator.class)
public @interface IbanAnnotation {
    String message() default "IBAN is not valid.";
    boolean allowNull();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
