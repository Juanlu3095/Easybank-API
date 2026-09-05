package com.jcooldevelopment.easybank_api.projections.operation;

import java.time.LocalDateTime;
import java.util.UUID;

// https://medium.com/@abdullahkhames96/understanding-projections-in-hibernate-a-brief-guide-2796d49d742b
/**
 * This projection is used to optimize SQL query for operations and its relationships. With this,
 * Hibernate will not make aditional database queries due to table relations.
 * It is used for admin users that needs orderer's account info.
 * The param's order must be the same as the used in repository custom query. If not, it will
 * throw an error.
 * @param id
 * @param concept
 * @param counterpartExternalAccountIban
 * @param createdAt
 * @param status
 * @param type
 * @param updatedAt
 * @param ordererAccountId
 * @param ordererAccountIban
 * @param ordererAccountBicswift
 * @param ordererAccountPlace
 * @param ordererName
 * @param ordererSurname
 * @param counterpartAccountId
 * @param counterpartAccountIban
 * @param counterpartAccountBicswift
 * @param counterpartAccountPlace
 */
public record OperationProjectionWithAccount(
    UUID id,
    String concept,
    String counterpartExternalAccountIban,
    LocalDateTime createdAt,
    String status,
    String type,
    LocalDateTime updatedAt,
    UUID ordererAccountId,
    String ordererAccountIban,
    String ordererAccountBicswift,
    String ordererAccountPlace,
    String ordererName,
    String ordererSurname,
    UUID counterpartAccountId,
    String counterpartAccountIban,
    String counterpartAccountBicswift,
    String counterpartAccountPlace
) {

}
