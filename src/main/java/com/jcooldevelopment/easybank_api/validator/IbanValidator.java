package com.jcooldevelopment.easybank_api.validator;

import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;
import org.iban4j.InvalidCheckDigitException;
import org.iban4j.UnsupportedCountryException;

import com.jcooldevelopment.easybank_api.annotations.IbanAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IbanValidator implements ConstraintValidator<IbanAnnotation, String>{

    // https://github.com/arturmkrtchyan/iban4j
    @Override
    public boolean isValid(String iban, ConstraintValidatorContext context) {
        try {
            IbanUtil.validate(iban);
            return true;
        } catch (IbanFormatException | InvalidCheckDigitException | UnsupportedCountryException ex) {
            return false;
        }
    } 
}
