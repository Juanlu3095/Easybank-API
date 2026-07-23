package com.jcooldevelopment.easybank_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcooldevelopment.easybank_api.contracts.entity.Account;

public interface AccountRepository extends JpaRepository<Account, UUID>{

}
