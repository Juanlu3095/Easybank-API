package com.jcooldevelopment.easybank_api.dto.AccountType;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountTypeDto {

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotBlank(message = "Name cannot be blank.")
    private String terms;
}
