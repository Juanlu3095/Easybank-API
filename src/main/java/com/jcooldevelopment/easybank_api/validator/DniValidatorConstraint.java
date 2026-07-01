package com.jcooldevelopment.easybank_api.validator;

import com.jcooldevelopment.easybank_api.annotations.DniValidatorAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DniValidatorConstraint implements ConstraintValidator<DniValidatorAnnotation, String>{

    @Override
    public boolean isValid(String dni, ConstraintValidatorContext context) {
         if (dni.length() != 9) { 
            return false;
        }
        
        char letter = dni.charAt(8);
        String numbers = dni.substring(0, 8);
        String dniLetters = "TRWAGMYFPDXBNJZSQVHLCKE";
        
        int formatedNumbers;
        
        // Verify if numbers has no number, only strings
        try {
            formatedNumbers = Integer.parseInt(numbers);
        } catch (NumberFormatException e) {
            return false;
        }
        
        int rest = formatedNumbers%23; // Divide DNI's numbers with 23 to get DNI's only char position
        
        if (dniLetters.charAt(rest) == Character.toUpperCase(letter)) { // If they are equals, DNI is correct
            return true;
        }
        
        return false;
    }
    
}
