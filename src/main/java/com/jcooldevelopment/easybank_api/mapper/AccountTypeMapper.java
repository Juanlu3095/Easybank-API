package com.jcooldevelopment.easybank_api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.AccountType;
import com.jcooldevelopment.easybank_api.dto.AccountType.AccountTypeDto;
import com.jcooldevelopment.easybank_api.dto.AccountType.CreateAccountTypeDto;
import com.jcooldevelopment.easybank_api.dto.AccountType.UpdateAccountTypeDto;

@Component
public class AccountTypeMapper {

    private final ModelMapper modelMapper;

    public AccountTypeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public AccountType CreateAccountTypeDtoToEntity(CreateAccountTypeDto createAccountTypeDto) {
       return modelMapper.map(createAccountTypeDto, AccountType.class);
    }

    public AccountType UpdateAccountTypeDtoToEntity(UpdateAccountTypeDto updateAccountTypeDto) {
       return modelMapper.map(updateAccountTypeDto, AccountType.class);
    }

    public AccountTypeDto EntityToDto(AccountType AccountType) {
        return modelMapper.map(AccountType, AccountTypeDto.class);
    }
}
