package com.jcooldevelopment.easybank_api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.jcooldevelopment.easybank_api.validator.EnumValidatorContraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

// https://docs.spring.io/spring-framework/reference/core/validation/beanvalidation.html#validation-beanvalidation-spring-constraints
// https://stackoverflow.com/questions/62372281/how-to-validate-enum-in-dto
// https://stackoverflow.com/questions/62666481/how-to-handle-org-springframework-http-converter-httpmessagenotreadableexceptio
// https://medium.com/@ajaysingh942/custom-validator-for-enum-in-java-237fad7e6db1
// https://www.baeldung.com/javax-validations-enums
@Target({
    ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumValidatorContraint.class)
@NotNull
public @interface EnumValidatorAnnotation {
    Class<? extends Enum<?>> enumClass();
    String message() default "Must be a valid Enum.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
