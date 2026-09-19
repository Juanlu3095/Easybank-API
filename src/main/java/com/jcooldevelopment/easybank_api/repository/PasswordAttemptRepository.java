package com.jcooldevelopment.easybank_api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.jcooldevelopment.easybank_api.contracts.entity.PasswordAttempt;

public interface PasswordAttemptRepository extends CrudRepository<PasswordAttempt, Integer>{

    Optional<PasswordAttempt> findByUsercode(String usercode);
}
