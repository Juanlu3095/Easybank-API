package com.jcooldevelopment.easybank_api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jcooldevelopment.easybank_api.validator.UserDniUniqueConstraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

/**
 * Since validations with database should not be done with DTO, this annotation will no be used.
 */
@Target({
    ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UserDniUniqueConstraint.class)
@NotNull
public @interface UserDniUniqueAnnotation {
    String message() default "This DNI already exists.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
