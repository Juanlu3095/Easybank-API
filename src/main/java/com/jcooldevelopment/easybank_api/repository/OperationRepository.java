package com.jcooldevelopment.easybank_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcooldevelopment.easybank_api.contracts.entity.Operation;

public interface OperationRepository extends JpaRepository<Operation, UUID>{

}
