package com.jcooldevelopment.easybank_api.service.Account;

import java.util.UUID;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.Account.AccountDto;
import com.jcooldevelopment.easybank_api.dto.Account.CreateAccountAdminDto;
import com.jcooldevelopment.easybank_api.dto.Account.CreateAccountDto;
import com.jcooldevelopment.easybank_api.dto.Account.UpdateAccountAdminDto;
import com.jcooldevelopment.easybank_api.dto.Account.UpdateAccountDto;

public interface AccountService {

    PaginatedResponse<AccountDto> getAll(int page, int size);

    PaginatedResponse<AccountDto> getAllByUser(int page, int size);
    
    AccountDto getById(UUID id); // Client must prove he has credentials for the given id

    AccountDto create(CreateAccountDto createAccountDto);

    AccountDto createByAdmin(CreateAccountAdminDto createAccountAdminDto);

    AccountDto update(UUID id, UpdateAccountDto updateAccountDto);

    AccountDto updateByAdmin(UUID id, UpdateAccountAdminDto updateAccountAdminDto);

    void delete(UUID id);
}
