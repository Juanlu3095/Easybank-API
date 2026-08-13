package com.jcooldevelopment.easybank_api.dto.Movement;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementPerOperationOnlyIban {

    private UUID id;

    private String accountIban;

    private BigDecimal amount;

    private UUID operationId;
}
