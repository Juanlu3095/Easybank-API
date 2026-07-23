package com.jcooldevelopment.easybank_api.service.Account;

import com.jcooldevelopment.easybank_api.repository.AccountRepository;

public class AccountServiceImpl{

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

}
