package com.jcooldevelopment.easybank_api.dto.Operation;

import java.math.BigDecimal;
import java.util.UUID;

import com.jcooldevelopment.easybank_api.annotations.IbanAnnotation;

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
public class CreateOperationAdminDto {

    @NotBlank(message = "Concept cannot be blank.")
    private String concept;

    @NotNull(message = "There is no account selected.")
    private UUID accountId;

    @IbanAnnotation(message = "IBAN not valid.")
    private String beneficiaryAccount;

    @Digits(integer = 17, fraction = 2, message = "The amount must have a maximum of 17 integers and 2 decimals.")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;
}
