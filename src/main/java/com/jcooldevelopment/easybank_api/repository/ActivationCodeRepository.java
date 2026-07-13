package com.jcooldevelopment.easybank_api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcooldevelopment.easybank_api.contracts.entity.ActivationCode;

public interface ActivationCodeRepository extends JpaRepository<ActivationCode, UUID>{

    Optional<ActivationCode> findByCode(String code);
}
