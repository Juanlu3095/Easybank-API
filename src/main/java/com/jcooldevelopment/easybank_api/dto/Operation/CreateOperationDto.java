package com.jcooldevelopment.easybank_api.dto.Operation;

import java.math.BigDecimal;
import java.util.UUID;

import com.jcooldevelopment.easybank_api.annotations.EnumValidatorAnnotation;
import com.jcooldevelopment.easybank_api.annotations.IbanAnnotation;
import com.jcooldevelopment.easybank_api.annotations.NotEnumValueAnnotation;
import com.jcooldevelopment.easybank_api.contracts.enums.OperationType;

import jakarta.validation.constraints.DecimalMin;
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

    @EnumValidatorAnnotation(
        enumClass = OperationType.class,
        message = "Operation type value not valid."
    )
    @NotEnumValueAnnotation(
        forbiddenValue = "BALANCE_ADJUSTMENT",
        message = "Operation type is not valid for this user."
    )
    private String operationType;

    @IbanAnnotation(message = "IBAN not valid.")
    private String beneficiaryAccount;

    @Digits(integer = 17, fraction = 2, message = "The amount must have a maximum of 17 integers and 2 decimals.")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;
}
