package com.jcooldevelopment.easybank_api.validator;

import com.jcooldevelopment.easybank_api.annotations.UserDniUniqueAnnotation;
import com.jcooldevelopment.easybank_api.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Since validations with database should not be done with DTO, this class will no be used.
 */
public class UserDniUniqueConstraint implements ConstraintValidator<UserDniUniqueAnnotation, String>{

    private final UserRepository userRepository;

    public UserDniUniqueConstraint(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public boolean isValid(String dni, ConstraintValidatorContext context) {
        return this.userRepository.countByDni(dni) == 0;
    }
}
