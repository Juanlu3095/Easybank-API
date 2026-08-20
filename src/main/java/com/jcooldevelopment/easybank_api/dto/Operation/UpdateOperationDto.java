package com.jcooldevelopment.easybank_api.dto.Operation;

import com.jcooldevelopment.easybank_api.contracts.enums.OperationStatus;
import com.jcooldevelopment.easybank_api.contracts.enums.OperationType;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOperationDto {

    @NotBlank(message = "Must indicate the operation type.")
    private OperationType operationType;

    @NotBlank(message = "Must indicate the status operation.")
    private OperationStatus status;

}
