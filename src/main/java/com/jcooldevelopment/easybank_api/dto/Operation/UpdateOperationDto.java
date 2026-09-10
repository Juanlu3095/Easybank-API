package com.jcooldevelopment.easybank_api.dto.Operation;

import com.jcooldevelopment.easybank_api.annotations.EnumValidatorAnnotation;
import com.jcooldevelopment.easybank_api.contracts.enums.OperationStatus;
import com.jcooldevelopment.easybank_api.contracts.enums.OperationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOperationDto {

    @EnumValidatorAnnotation(
        enumClass = OperationType.class,
        allowNull = false,
        message = "Operation type value not valid."
    )
    private String operationType;

    @EnumValidatorAnnotation(
        enumClass = OperationStatus.class,
        allowNull = false,
        message = "Operation status value not valid."
    )
    private String status;

}
