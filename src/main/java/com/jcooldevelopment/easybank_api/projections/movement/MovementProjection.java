package com.jcooldevelopment.easybank_api.projections.movement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record MovementProjection(
    UUID id,
    BigDecimal amount,
    LocalDateTime createdAt,
    String externalAccountIban,
    LocalDateTime updatedAt,
    String accountIban,
    UUID operationId
) {

}
