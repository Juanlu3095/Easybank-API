package com.jcooldevelopment.easybank_api.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Meta;
import org.springframework.data.jpa.repository.NativeQuery;

import com.jcooldevelopment.easybank_api.contracts.entity.Operation;

public interface OperationRepository extends JpaRepository<Operation, UUID>{

    @Meta(comment = "Search operations asigned to a user's id")
    @NativeQuery("SELECT * FROM operation" +
        " INNER JOIN account ON operation.orderer_account_id = account.id" +
        " INNER JOIN user_account ON account.id = user_account.account_id" +
        " INNER JOIN users ON user_account.user_id = users.id" +
        " WHERE users.id = ?1"
    )    
    Page<Operation> findByUser(UUID id, Pageable pageable);

    Page<Operation> findByOrdererAccountId(UUID accountId, Pageable pageable);
}
