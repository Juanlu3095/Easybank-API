package com.jcooldevelopment.easybank_api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jcooldevelopment.easybank_api.contracts.entity.Movement;
import com.jcooldevelopment.easybank_api.projections.movement.MovementProjection;

public interface MovementRepository extends JpaRepository<Movement, UUID>{

    @Query(
        value = """
        SELECT movement.id,
        movement.amount,
        movement.created_at as createdAt,
        movement.external_account as externalAccount,
        movement.updated_at as updatedAt,
        account.iban as accountIban,
        movement.operation_id as operationId
        FROM movement
        INNER JOIN account
        ON movement.account_id = account.id
        WHERE movement.operation_id IN ?1
        """,
        nativeQuery = true
    )
    List<MovementProjection> findByOperationIds(List<UUID> uuids);

    @Query(
        value = """
        SELECT movement.id,
        movement.amount,
        movement.created_at as createdAt,
        movement.external_account as externalAccount,
        movement.updated_at as updatedAt,
        account.iban as accountIban,
        movement.operation_id as operationId
        FROM movement
        INNER JOIN account
        ON movement.account_id = account.id
        WHERE movement.operation_id = ?1
        """,
        nativeQuery = true
    )
    List<MovementProjection> findByOperationId(UUID uuid);
}
