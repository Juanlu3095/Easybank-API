package com.jcooldevelopment.easybank_api.dto.Operation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

import com.jcooldevelopment.easybank_api.contracts.enums.OperationStatus;
import com.jcooldevelopment.easybank_api.contracts.enums.OperationType;
import com.jcooldevelopment.easybank_api.dto.Movement.MovementPerOperationOnlyIban;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO used for client role, only info needed.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationDto {

    private UUID id;

    private String concept;

    private OperationStatus status;

    private OperationType type;

    private String counterpartAccountIban;

    private String orderer;

    private String ordererAccountIban;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<MovementPerOperationOnlyIban> movements;

    public void addMovement(MovementPerOperationOnlyIban movement){
        if (this.movements == null) {
            this.movements = new ArrayList<>();
        }
        this.movements.add(movement);
    }
}
