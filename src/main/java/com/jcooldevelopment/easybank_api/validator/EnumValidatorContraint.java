package com.jcooldevelopment.easybank_api.validator;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jcooldevelopment.easybank_api.annotations.EnumValidatorAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidatorContraint implements ConstraintValidator<EnumValidatorAnnotation, String>{

    Set<String> values;

    @Override
    public void initialize(EnumValidatorAnnotation constraintAnnotation) {
        values = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
            .map(value -> value.name())
            .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.values.contains(value);
    }

}
