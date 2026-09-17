package com.jcooldevelopment.easybank_api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.jcooldevelopment.easybank_api.contracts.entity.PinAttempt;

// CrudRepository is the generic one, if JpaRepository is used, Spring will think there are two different beans
public interface PinAttemptRepository extends CrudRepository<PinAttempt, Integer>{

    Optional<PinAttempt> findByUsercode(String usercode);
}
