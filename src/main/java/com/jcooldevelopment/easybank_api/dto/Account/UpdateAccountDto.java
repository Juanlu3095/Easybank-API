package com.jcooldevelopment.easybank_api.dto.Account;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountDto {

    @NotNull(message="There is no branch selected.")
    private UUID branchId;
}
