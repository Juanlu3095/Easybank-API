package com.jcooldevelopment.easybank_api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jcooldevelopment.easybank_api.validator.EnumForbiddenValueConstraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

@Target({
    ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumForbiddenValueConstraint.class)
@NotNull
public @interface NotEnumValueAnnotation {
    String message() default "Field cannot use this value.";
    String forbiddenValue();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
