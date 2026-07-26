package com.jcooldevelopment.easybank_api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.Account;
import com.jcooldevelopment.easybank_api.dto.Account.AccountDto;
import com.jcooldevelopment.easybank_api.dto.Account.CreateAccountDto;
import com.jcooldevelopment.easybank_api.dto.Account.UpdateAccountDto;

@Component
public class AccountMapper {

    private final ModelMapper modelMapper;

    public AccountMapper (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Account CreateAccountDtoToEntity(CreateAccountDto createAccountDto) {
       return modelMapper.map(createAccountDto, Account.class);
    }

    public Account UpdateAccountDtoToEntity(UpdateAccountDto updateAccountDto) {
       return modelMapper.map(updateAccountDto, Account.class);
    }

    public AccountDto EntityToDto(Account Account) {
        return modelMapper.map(Account, AccountDto.class);
    }
}
