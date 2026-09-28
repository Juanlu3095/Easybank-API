package com.jcooldevelopment.easybank_api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jcooldevelopment.easybank_api.validator.DniValidatorConstraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({
    ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DniValidatorConstraint.class)
public @interface DniValidatorAnnotation {
    String message() default "DNI format is not valid.";
    boolean allowNull(); // This allows to use null in case we use it as filter in GET queries
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
