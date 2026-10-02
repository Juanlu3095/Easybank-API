package com.jcooldevelopment.easybank_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jcooldevelopment.easybank_api.contracts.entity.Message;

public interface MessageRepository extends JpaRepository<Message, UUID>, JpaSpecificationExecutor<Message>{

}
