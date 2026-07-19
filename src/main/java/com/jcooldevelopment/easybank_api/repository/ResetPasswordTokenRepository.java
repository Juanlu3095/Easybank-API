package com.jcooldevelopment.easybank_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcooldevelopment.easybank_api.contracts.entity.ResetPasswordToken;

import jakarta.transaction.Transactional;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, UUID>{

    // https://stackoverflow.com/questions/32269192/spring-no-entitymanager-with-actual-transaction-available-for-current-thread
    // https://keepcoding.io/blog/que-es-transactional-en-spring-boot/
    @Transactional // It allows to run queries in a safe form. If an error occurs, it will not do any change in database
    void deleteByUser_id(UUID id);
}
