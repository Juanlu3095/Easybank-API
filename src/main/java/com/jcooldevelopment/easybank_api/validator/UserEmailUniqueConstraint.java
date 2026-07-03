package com.jcooldevelopment.easybank_api.validator;

import com.jcooldevelopment.easybank_api.annotations.UserEmailUniqueAnnotation;
import com.jcooldevelopment.easybank_api.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserEmailUniqueConstraint implements ConstraintValidator<UserEmailUniqueAnnotation, String>{

    private final UserRepository userRepository;

    public UserEmailUniqueConstraint(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !this.userRepository.existsByEmail(email);
    }

    
}
