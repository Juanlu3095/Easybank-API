package com.jcooldevelopment.easybank_api.dto.Operation;

import java.math.BigDecimal;
import java.util.UUID;

import com.jcooldevelopment.easybank_api.annotations.IbanAnnotation;
import com.jcooldevelopment.easybank_api.contracts.enums.OperationType;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOperationDto {

    @NotBlank(message = "Concept cannot be blank.")
    private String concept;

    @NotNull(message = "There is no account selected.")
    private UUID accountId;

    @NotNull(message = "Must indicate the operation type.")
    private OperationType operationType;

    @IbanAnnotation(message = "IBAN not valid.")
    private String beneficiaryAccount;

    @Digits(integer = 17, fraction = 2, message = "The amount must have a maximum of 17 integers and 2 decimals.")
    private BigDecimal amount;
}
