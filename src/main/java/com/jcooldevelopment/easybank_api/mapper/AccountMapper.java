package com.jcooldevelopment.easybank_api.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.Account;
import com.jcooldevelopment.easybank_api.dto.Account.AccountAdminDto;
import com.jcooldevelopment.easybank_api.dto.Account.AccountDto;
import com.jcooldevelopment.easybank_api.dto.Account.AccountDtoNoUsers;
import com.jcooldevelopment.easybank_api.dto.Account.CreateAccountDto;
import com.jcooldevelopment.easybank_api.dto.User.UserDto;

@Component
public class AccountMapper {

    private final BranchMapper branchMapper;
    private final ModelMapper modelMapper;
    private final UserMapper userMapper;

    public AccountMapper (ModelMapper modelMapper, BranchMapper branchMapper, UserMapper userMapper) {
        this.modelMapper = modelMapper;
        this.branchMapper = branchMapper;
        this.userMapper = userMapper;
    }

    public Account CreateAccountDtoToEntity(CreateAccountDto createAccountDto) {
       return modelMapper.map(createAccountDto, Account.class);
    }

    public AccountAdminDto AdminEntityToDto(Account account) {
        AccountAdminDto accountAdminDto = new AccountAdminDto();
        accountAdminDto.setAccountType(account.getAccountType());
        accountAdminDto.setBalance(account.getBalance());
        accountAdminDto.setBicSwift(account.getBicSwift());
        accountAdminDto.setBranch(account.getBranch());
        accountAdminDto.setIban(account.getIban());
        accountAdminDto.setId(account.getId());
        accountAdminDto.setStatus(account.getStatus());
        accountAdminDto.setAccountPurpose(account.getAccountPurpose());

        List<UserDto> users = new ArrayList<UserDto>();

        account.getUsers().forEach((user) -> users.add(this.userMapper.EntityToDto(user)));
        accountAdminDto.setUsers(users);

        return accountAdminDto;
    }

    public AccountDto EntityToDto(Account account){
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBalance(account.getBalance());
        accountDto.setBicSwift(account.getBicSwift());
        accountDto.setIban(account.getIban());
        accountDto.setId(account.getId());
        accountDto.setStatus(account.getStatus());
        accountDto.setBranch(this.branchMapper.EntityToDto(account.getBranch()));

        List<UserDto> users = new ArrayList<UserDto>();

        account.getUsers().forEach((user) -> users.add(this.userMapper.EntityToDto(user)));
        accountDto.setUsers(users);
        return accountDto;
    }

    // When is redundant to have users in data
    public AccountDtoNoUsers EntityToDtoNoUsers(Account account){
        AccountDtoNoUsers accountDto = new AccountDtoNoUsers();
        accountDto.setId(account.getId());
        accountDto.setIban(account.getIban());
        accountDto.setBicSwift(account.getBicSwift());
        accountDto.setPlace(account.getBranch().getName());
        return accountDto;
    }
}
