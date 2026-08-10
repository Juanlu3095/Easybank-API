package com.jcooldevelopment.easybank_api.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Meta;
import org.springframework.data.jpa.repository.Query;

import com.jcooldevelopment.easybank_api.contracts.entity.Operation;

public interface OperationRepository extends JpaRepository<Operation, UUID>{

    // Duplicate sql alias for using SELECT *: https://stackoverflow.com/questions/78211463/encountered-a-duplicated-sql-alias-id-during-auto-discovery-of-a-native-sql-qu
    // Pagination in native query: https://stackoverflow.com/questions/38349930/spring-data-and-native-query-with-pagination
    @Meta(comment = "Search operations asigned to a user's id")
    @Query(value = "SELECT operation.id, operation.concept, operation.counterpart_external_account_iban, operation.counterpart_account_id" +
        ", operation.created_at, operation.status, operation.type, operation.updated_at, operation.orderer_account_id" +
        " FROM operation" +
        " INNER JOIN account ON operation.orderer_account_id = account.id" +
        " INNER JOIN user_account ON account.id = user_account.account_id" +
        " INNER JOIN users ON user_account.user_id = users.id" +
        " WHERE users.id = ?1" + 
        " ORDER BY operation.created_at DESC ",
        // CountQuery to tell JPA about info for pagination
        countQuery = """
        SELECT COUNT(*)
        FROM operation
        INNER JOIN account ON operation.orderer_account_id = account.id
        INNER JOIN user_account ON account.id = user_account.account_id
        INNER JOIN users ON user_account.user_id = users.id
        WHERE users.id = ?1 
        """,
        nativeQuery = true
    )    
    Page<Operation> findByUser(UUID id, Pageable pageable);

    Page<Operation> findByOrdererAccountId(UUID accountId, Pageable pageable);
}
