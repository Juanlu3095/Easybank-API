package com.jcooldevelopment.easybank_api.validator;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jcooldevelopment.easybank_api.annotations.EnumValidatorAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidatorContraint implements ConstraintValidator<EnumValidatorAnnotation, String>{

    Set<String> values;
    boolean allowNull;

    @Override
    public void initialize(EnumValidatorAnnotation constraintAnnotation) {
        values = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
            .map(value -> value.name())
            .collect(Collectors.toSet());
        
        // For filters in GET queries
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(allowNull && value == null) return true;
        return this.values.contains(value);
    }

}
