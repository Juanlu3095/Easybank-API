package com.jcooldevelopment.easybank_api.dto.Operation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jcooldevelopment.easybank_api.contracts.enums.OperationStatus;
import com.jcooldevelopment.easybank_api.contracts.enums.OperationType;
import com.jcooldevelopment.easybank_api.dto.Account.AccountDtoNoUsers;
import com.jcooldevelopment.easybank_api.dto.Movement.MovementPerOperationDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationAdminDto {

    private UUID id;

    private String concept;

    private OperationStatus status;

    private OperationType type;

    private AccountDtoNoUsers counterpartAccount;

    private String counterpartExternalAccount;

    private String orderer;

    private AccountDtoNoUsers ordererAccount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<MovementPerOperationDto> movements;

    public void addMovement(MovementPerOperationDto movement){
        if (this.movements == null) {
            this.movements = new ArrayList<>();
        }
        this.movements.add(movement);
    }
}
