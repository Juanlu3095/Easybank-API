package com.jcooldevelopment.easybank_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcooldevelopment.easybank_api.contracts.entity.IncidenceType;

public interface IncidenceTypeRepository extends JpaRepository<IncidenceType, Integer>{
    Optional<IncidenceType> findByName(String name);
}
