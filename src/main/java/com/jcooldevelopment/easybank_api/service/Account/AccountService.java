package com.jcooldevelopment.easybank_api.service.Account;

import java.util.UUID;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.Account.AccountDto;
import com.jcooldevelopment.easybank_api.dto.Account.CreateAccountDto;
import com.jcooldevelopment.easybank_api.dto.Account.UpdateAccountDto;

public interface AccountService {

    PaginatedResponse<AccountDto> getAll(int page, int size);

    AccountDto getById(UUID id);

    AccountDto create(CreateAccountDto createAccountDto);

    AccountDto update(UpdateAccountDto updateAccountDto);

    boolean delete(UUID id);
}
