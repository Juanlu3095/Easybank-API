package com.jcooldevelopment.easybank_api.dto.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.jcooldevelopment.easybank_api.contracts.entity.AccountType;
import com.jcooldevelopment.easybank_api.contracts.entity.Branch;
import com.jcooldevelopment.easybank_api.contracts.enums.AccountPurpose;
import com.jcooldevelopment.easybank_api.contracts.enums.AccountStatus;
import com.jcooldevelopment.easybank_api.dto.User.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountAdminDto {

    private UUID id;

    private BigDecimal balance;

    private String iban;

    private String bicSwift;

    private AccountStatus status;

    private AccountType accountType;

    private AccountPurpose accountPurpose;

    private Branch branch;

    private List<UserDto> users;
}
