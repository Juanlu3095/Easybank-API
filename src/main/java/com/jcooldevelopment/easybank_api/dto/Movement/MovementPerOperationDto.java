package com.jcooldevelopment.easybank_api.dto.Movement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.jcooldevelopment.easybank_api.dto.Account.AccountDtoNoUsers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Movement with no info about operation when it is returned with operation entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementPerOperationDto {

    private UUID id;

    private AccountDtoNoUsers account;

    private String externalAccount;

    private BigDecimal amount;
    
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
