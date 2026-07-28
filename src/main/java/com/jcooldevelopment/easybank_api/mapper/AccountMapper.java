package com.jcooldevelopment.easybank_api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.Account;
import com.jcooldevelopment.easybank_api.dto.Account.AccountAdminDto;
import com.jcooldevelopment.easybank_api.dto.Account.AccountDto;
import com.jcooldevelopment.easybank_api.dto.Account.CreateAccountDto;

@Component
public class AccountMapper {

    private final ModelMapper modelMapper;

    public AccountMapper (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Account CreateAccountDtoToEntity(CreateAccountDto createAccountDto) {
       return modelMapper.map(createAccountDto, Account.class);
    }

    public AccountAdminDto AdminEntityToDto(Account account) {
        return modelMapper.map(account, AccountAdminDto.class);
    }

    public AccountDto EntityToDto(Account account){
        return modelMapper.map(account, AccountDto.class);
    }
}
