package com.jcooldevelopment.easybank_api.projections.movement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record MovementProjectionWithAccountNoUsers(
    UUID id,
    UUID accountId,
    String accountIban,
    String accountBicSwift,
    String accountPlace,
    String externalAccount,
    BigDecimal amount,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    UUID operationId
) {

}
