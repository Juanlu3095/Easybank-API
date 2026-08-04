package com.jcooldevelopment.easybank_api.dto.Account;

import java.util.List;
import java.util.UUID;

import com.jcooldevelopment.easybank_api.contracts.enums.AccountStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountAdminDto {

    @NotNull(message="There is no branch selected.")
    private Long branchId; // User will select his/her preferred branch from a list

    @NotNull(message="There is no status selected.")
    private AccountStatus status;

    @NotNull(message="There is no user selected.")
    private List<UUID> userIds;

    @NotNull(message="There is no account type selected.")
    private UUID accountTypeId;

    // bank code is taken from application.properties
    // client account code is generated automatically
    // Bic bank entity is always 4 chars. To our bank is a 4 length string in application.properties
}
