package com.jcooldevelopment.easybank_api.service.AccountType;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.entity.AccountType;
import com.jcooldevelopment.easybank_api.dto.AccountType.AccountTypeDto;
import com.jcooldevelopment.easybank_api.dto.AccountType.CreateAccountTypeDto;
import com.jcooldevelopment.easybank_api.dto.AccountType.UpdateAccountTypeDto;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.mapper.AccountTypeMapper;
import com.jcooldevelopment.easybank_api.repository.AccountTypeRepository;

@Service
public class AccountTypeServiceImpl implements AccountTypeService{

    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapper accountTypeMapper;

    public AccountTypeServiceImpl(AccountTypeRepository accountTypeRepository, AccountTypeMapper accountTypeMapper) {
        this.accountTypeRepository = accountTypeRepository;
        this.accountTypeMapper = accountTypeMapper;
    }

    @Override
    public List<AccountTypeDto> getAll() {
        List<AccountType> accountTypes = this.accountTypeRepository.findAll();
        return accountTypes.stream()
            .map((accountType) -> accountTypeMapper.EntityToDto(accountType))
            .toList();
    }

    @Override
    public AccountTypeDto getById(UUID id) {
        AccountType accountType = this.accountTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Account type not found."));
        
        return this.accountTypeMapper.EntityToDto(accountType);
    }

    @Override
    public AccountTypeDto create(CreateAccountTypeDto createAccountTypeDto) {
        AccountType accountType = this.accountTypeMapper.CreateAccountTypeDtoToEntity(createAccountTypeDto);
        AccountType accountTypeToSave = this.accountTypeRepository.save(accountType);
        return accountTypeMapper.EntityToDto(accountTypeToSave);
    }

    @Override
    public AccountTypeDto update(UUID id, UpdateAccountTypeDto updateAccountTypeDto) {
        AccountType accountType = this.accountTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Account type not found."));

        accountType.setName(updateAccountTypeDto.getName());
        accountType.setTerms(updateAccountTypeDto.getTerms());
        AccountType accountTypeToSave = this.accountTypeRepository.save(accountType);
        return accountTypeMapper.EntityToDto(accountTypeToSave);
    }

    @Override
    public boolean delete(UUID id) {
        AccountType accountType = this.accountTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Account type not found."));

        this.accountTypeRepository.delete(accountType);
        return true;
    }

}
