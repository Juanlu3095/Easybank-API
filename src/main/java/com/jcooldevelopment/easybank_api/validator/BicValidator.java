package com.jcooldevelopment.easybank_api.validator;

import org.iban4j.BicFormatException;
import org.iban4j.BicUtil;

import com.jcooldevelopment.easybank_api.annotations.BicAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BicValidator implements ConstraintValidator<BicAnnotation, String>{

    // https://github.com/arturmkrtchyan/iban4j
    @Override
    public boolean isValid(String bic, ConstraintValidatorContext context) {
        try {
            BicUtil.validate(bic);
            return true;
        } catch (BicFormatException ex) {
            return false;
        }
    }

}
