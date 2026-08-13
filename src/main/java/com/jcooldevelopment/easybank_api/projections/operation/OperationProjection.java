package com.jcooldevelopment.easybank_api.projections.operation;

import java.time.LocalDateTime;
import java.util.UUID;

// https://medium.com/@abdullahkhames96/understanding-projections-in-hibernate-a-brief-guide-2796d49d742b
/**
 * This projection is used to optimize SQL query for operations and its relationships. With this,
 * Hibernate will not make aditional database queries due to table relations.
 * The param's order must be the same as the used in repository custom query. If not, it will
 * throw an error.
 * @param id
 * @param concept
 * @param counterpartExternalAccountIban
 * @param counterpartAccountIban
 * @param createdAt
 * @param status
 * @param type
 * @param updatedAt
 * @param ordererAccountIban
 */
public record OperationProjection(
    UUID id,
    String concept,
    String counterpartExternalAccountIban,
    String counterpartAccountIban,
    LocalDateTime createdAt,
    String status,
    String type,
    LocalDateTime updatedAt,
    String ordererAccountIban
) {

}
