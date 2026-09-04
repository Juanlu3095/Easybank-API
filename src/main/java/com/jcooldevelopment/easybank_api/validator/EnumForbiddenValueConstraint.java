package com.jcooldevelopment.easybank_api.validator;

import com.jcooldevelopment.easybank_api.annotations.NotEnumValueAnnotation;
import com.jcooldevelopment.easybank_api.contracts.enums.OperationType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumForbiddenValueConstraint implements ConstraintValidator<NotEnumValueAnnotation, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return false;

        try {
            return !(OperationType.valueOf(value).equals(OperationType.BALANCE_ADJUSTMENT));

        } catch(IllegalArgumentException exception){
            return false;
        }
    }
    
}
