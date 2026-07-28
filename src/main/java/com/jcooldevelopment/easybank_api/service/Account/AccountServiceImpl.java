package com.jcooldevelopment.easybank_api.service.Account;

import java.math.BigDecimal;
import java.util.UUID;

import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.contracts.entity.Account;
import com.jcooldevelopment.easybank_api.contracts.entity.AccountType;
import com.jcooldevelopment.easybank_api.contracts.entity.Branch;
import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.contracts.enums.AccountStatus;
import com.jcooldevelopment.easybank_api.dto.Account.AccountAdminDto;
import com.jcooldevelopment.easybank_api.dto.Account.AccountDto;
import com.jcooldevelopment.easybank_api.dto.Account.CreateAccountAdminDto;
import com.jcooldevelopment.easybank_api.dto.Account.CreateAccountDto;
import com.jcooldevelopment.easybank_api.dto.Account.UpdateAccountAdminDto;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.mapper.AccountMapper;
import com.jcooldevelopment.easybank_api.repository.AccountRepository;
import com.jcooldevelopment.easybank_api.repository.AccountTypeRepository;
import com.jcooldevelopment.easybank_api.repository.BranchRepository;
import com.jcooldevelopment.easybank_api.repository.UserRepository;
import com.jcooldevelopment.easybank_api.utils.DataFormater;

@Service
public class AccountServiceImpl implements AccountService{

    private final Environment env;
    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(
        Environment environment,
        AccountRepository accountRepository,
        AccountTypeRepository accountTypeRepository,
        UserRepository userRepository,
        BranchRepository branchRepository,
        AccountMapper accountMapper
    ) {
        this.env = environment;
        this.accountRepository = accountRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.userRepository = userRepository;
        this.branchRepository = branchRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public PaginatedResponse<AccountAdminDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Account::getCreatedAt).descending());
        Page<Account> accounts = this.accountRepository.findAll(pageable);
        Page<AccountAdminDto> accountsToShow = new PageImpl<AccountAdminDto>(accounts.getContent()
            .stream()
            .map(account -> accountMapper.AdminEntityToDto(account))
            .toList());
        return DataFormater.paginate(accountsToShow);
    }

    @Override
    public PaginatedResponse<AccountDto> getAllByUser(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Account::getCreatedAt).descending());
        // Get user from JWT
        String usercode = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Username: " + usercode);
        User user = this.userRepository.findByUsercode(usercode)
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        Page<Account> accounts = this.accountRepository.findByUser(pageable, user);
        Page<AccountDto> accountsToShow = new PageImpl<AccountDto>(accounts.getContent()
            .stream()
            .map(account -> accountMapper.EntityToDto(account))
            .toList());
        return DataFormater.paginate(accountsToShow);
    }

    @Override
    public AccountAdminDto getByIdForAdmin(UUID id) {
        Account account = this.accountRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found."));
        return this.accountMapper.AdminEntityToDto(account);
    }

    @Override
    public AccountDto getById(UUID id) {
        Account account = this.accountRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found."));
        return this.accountMapper.EntityToDto(account);
    }

    @Override
    public AccountDto create(CreateAccountDto createAccountDto) {
        AccountType accountType = this.accountTypeRepository.findById(createAccountDto.getAccountTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("Account type not found."));

        Branch branch = this.branchRepository.findById(createAccountDto.getBranchId())
            .orElseThrow(() -> new ResourceNotFoundException("Branch not found."));

        String usercode = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByUsercode(usercode)
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        // https://github.com/arturmkrtchyan/iban4j
        Iban iban = new Iban.Builder()
            .countryCode(CountryCode.getByCode(branch.getCountry().getCode())) // https://www.javatips.net/api/iban4j-master/src/main/java/org/iban4j/Iban.java
            .bankCode(env.getProperty("BANK.CODE"))
            .branchCode(branch.getIbanCode())
            .buildRandom();
        
        String bic = env.getProperty("BANK.ENTITY") + 
            branch.getCountry().getCode() + 
            branch.getLocalizationCode() + 
            (branch.getBicCode() == null ? "" : branch.getBicCode());

        Account accountToCreate = new Account();
        accountToCreate.setAccountType(accountType);
        accountToCreate.setBalance(new BigDecimal(0.00));
        accountToCreate.setBicSwift(bic);
        accountToCreate.setBranch(branch);
        accountToCreate.setIban(iban.toString()); // Careful with iban.toFormattedString(), it gives wrong length because spaces
        accountToCreate.setStatus(AccountStatus.ACTIVATED);
        accountToCreate.setUser(user);

        Account savedAccount = this.accountRepository.save(accountToCreate);
        return this.accountMapper.EntityToDto(savedAccount); // This should be more secure
    }

    @Override
    public AccountAdminDto createByAdmin(CreateAccountAdminDto createAccountAdminDto) {
        AccountType accountType = this.accountTypeRepository.findById(createAccountAdminDto.getAccountTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("Account type not found."));

        Branch branch = this.branchRepository.findById(createAccountAdminDto.getBranchId())
            .orElseThrow(() -> new ResourceNotFoundException("Branch not found."));

        User user = this.userRepository.findById(createAccountAdminDto.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Iban iban = new Iban.Builder()
            .countryCode(CountryCode.getByCode(branch.getCountry().getCode()))
            .bankCode(env.getProperty("BANK.CODE"))
            .branchCode(branch.getIbanCode())
            .buildRandom();
        
        String bic = env.getProperty("BANK.ENTITY") + 
            branch.getCountry().getCode() + 
            branch.getLocalizationCode() + 
            (branch.getBicCode() == null ? "" : branch.getBicCode());

        Account accountToCreate = new Account();
        accountToCreate.setAccountType(accountType);
        accountToCreate.setBalance(new BigDecimal(0.00));
        accountToCreate.setBicSwift(bic);
        accountToCreate.setBranch(branch);
        accountToCreate.setIban(iban.toString());
        accountToCreate.setStatus(AccountStatus.valueOf(createAccountAdminDto.getStatus().toString()));
        accountToCreate.setUser(user);

        Account savedAccount = this.accountRepository.save(accountToCreate);
        return this.accountMapper.AdminEntityToDto(savedAccount); // This should be more secure
    }

    @Override
    public AccountAdminDto updateByAdmin(UUID id, UpdateAccountAdminDto updateAccountAdminDto) {
        Account account = this.accountRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found."));

        if(updateAccountAdminDto.getAccountTypeId() != account.getAccountType().getId()) {
            AccountType accountType = this.accountTypeRepository.findById(updateAccountAdminDto.getAccountTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Account type not found."));

            account.setAccountType(accountType);
        }

        if(updateAccountAdminDto.getBranchId() != account.getBranch().getId()) {
            Branch branch = this.branchRepository.findById(updateAccountAdminDto.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found."));

            account.setBranch(branch);
        }

        if(!updateAccountAdminDto.getStatus().equals(account.getStatus())) {
            account.setStatus(updateAccountAdminDto.getStatus());
        }

        Account accountToSave = this.accountRepository.save(account);
        return this.accountMapper.AdminEntityToDto(accountToSave);
    }

    @Override
    public void delete(UUID id) {
        Account account = this.accountRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found."));

        this.accountRepository.delete(account);
    }

}
