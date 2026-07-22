package com.jcooldevelopment.easybank_api.dto.AccountType;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTypeDto {

    private UUID id;

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotBlank(message = "Name cannot be blank.")
    private String terms;
}
