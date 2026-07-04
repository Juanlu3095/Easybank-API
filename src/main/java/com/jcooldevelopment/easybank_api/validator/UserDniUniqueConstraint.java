package com.jcooldevelopment.easybank_api.validator;

import com.jcooldevelopment.easybank_api.annotations.UserDniUniqueAnnotation;
import com.jcooldevelopment.easybank_api.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserDniUniqueConstraint implements ConstraintValidator<UserDniUniqueAnnotation, String>{

    private final UserRepository userRepository;

    public UserDniUniqueConstraint(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public boolean isValid(String dni, ConstraintValidatorContext context) {
        return !this.userRepository.existsByDni(dni);
    }
}
