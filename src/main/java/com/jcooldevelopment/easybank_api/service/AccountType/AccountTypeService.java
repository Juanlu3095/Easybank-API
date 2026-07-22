package com.jcooldevelopment.easybank_api.service.AccountType;

import java.util.List;
import java.util.UUID;

import com.jcooldevelopment.easybank_api.dto.AccountType.AccountTypeDto;
import com.jcooldevelopment.easybank_api.dto.AccountType.CreateAccountTypeDto;
import com.jcooldevelopment.easybank_api.dto.AccountType.UpdateAccountTypeDto;

public interface AccountTypeService {

    List<AccountTypeDto> getAll();

    AccountTypeDto getById(UUID id);

    AccountTypeDto create(CreateAccountTypeDto createAccountTypeDto);

    AccountTypeDto update(UUID id, UpdateAccountTypeDto updateAccountTypeDto);

    boolean delete(UUID id);
}
