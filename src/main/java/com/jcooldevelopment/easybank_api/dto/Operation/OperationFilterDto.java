package com.jcooldevelopment.easybank_api.dto.Operation;

import com.jcooldevelopment.easybank_api.annotations.EnumValidatorAnnotation;
import com.jcooldevelopment.easybank_api.annotations.IbanAnnotation;
import com.jcooldevelopment.easybank_api.contracts.enums.OperationStatus;
import com.jcooldevelopment.easybank_api.contracts.enums.OperationType;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class OperationFilterDto {

    // Integer + @Min allows to use null and we will not get an error.
    @Min(value = 1, message = "Page minimal value is 1.")
    private Integer page = 1; // 1 is default value if there is no one provided.

    @Min(value = 1, message = "Page minimal size is 1.")
    private Integer size = 5;

    private String concept = "";

    @EnumValidatorAnnotation(enumClass = OperationStatus.class, allowNull = true, message = "Status value is not valid.")
    private String status;

    @EnumValidatorAnnotation(enumClass = OperationType.class, allowNull = true, message = "Operation type value is not valid.")
    private String type;

    @IbanAnnotation(message = "IBAN for orderer is not valid.", allowNull = true)
    private String ordererIban;

    @IbanAnnotation(message = "IBAN for beneficiary is not valid.", allowNull = true)
    private String counterpartIban;
}
