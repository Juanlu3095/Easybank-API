package com.jcooldevelopment.easybank_api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcooldevelopment.easybank_api.contracts.entity.OperationAuthorization;

public interface OperationAuthorizationRepository extends JpaRepository<OperationAuthorization, UUID> {

    Optional<OperationAuthorization> findByOperationId(UUID operationId);
}
