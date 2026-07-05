package com.jcooldevelopment.easybank_api.validator;

import com.jcooldevelopment.easybank_api.annotations.UserEmailUniqueAnnotation;
import com.jcooldevelopment.easybank_api.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Since validations with database should not be done with DTO, this annotation will no be used.
 */
public class UserEmailUniqueConstraint implements ConstraintValidator<UserEmailUniqueAnnotation, String>{

    private final UserRepository userRepository;

    public UserEmailUniqueConstraint(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return this.userRepository.countByEmail(email) == 0;
    }

}
