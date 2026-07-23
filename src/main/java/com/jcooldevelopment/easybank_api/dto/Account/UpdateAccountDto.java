package com.jcooldevelopment.easybank_api.dto.Account;

import com.jcooldevelopment.easybank_api.contracts.entity.AccountType;
import com.jcooldevelopment.easybank_api.contracts.entity.Branch;
import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.contracts.enums.AccountStatus;

import jakarta.validation.constraints.NotNull;

public class UpdateAccountDto {

    @NotNull(message="There is no branch selected.")
    private Branch branch; // User will select his/her preferred branch from a list

    @NotNull(message="There is no status selected.")
    private AccountStatus status;

    @NotNull(message="There is no incidence typeuser selected.")
    private User user;

    @NotNull(message="There is no account type selected.")
    private AccountType account_type;
}
