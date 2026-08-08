package com.jcooldevelopment.easybank_api.service.Operation;

import java.util.UUID;

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
import com.jcooldevelopment.easybank_api.contracts.entity.Movement;
import com.jcooldevelopment.easybank_api.contracts.entity.Operation;
import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.contracts.enums.AccountStatus;
import com.jcooldevelopment.easybank_api.contracts.enums.OperationStatus;
import com.jcooldevelopment.easybank_api.dto.Operation.CreateOperationDto;
import com.jcooldevelopment.easybank_api.dto.Operation.OperationDto;
import com.jcooldevelopment.easybank_api.dto.Operation.UpdateOperationDto;
import com.jcooldevelopment.easybank_api.exception.AccountNotActivatedException;
import com.jcooldevelopment.easybank_api.exception.NotEnoughBalanceException;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.mapper.OperationMapper;
import com.jcooldevelopment.easybank_api.repository.AccountRepository;
import com.jcooldevelopment.easybank_api.repository.MovementRepository;
import com.jcooldevelopment.easybank_api.repository.OperationRepository;
import com.jcooldevelopment.easybank_api.repository.UserRepository;
import com.jcooldevelopment.easybank_api.utils.DataFormater;

@Service
public class OperationServiceImpl implements OperationService{

    private final OperationRepository operationRepository;
    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final OperationMapper operationMapper;
    private final Environment env;

    public OperationServiceImpl(
        OperationRepository operationRepository,
        MovementRepository movementRepository,
        AccountRepository accountRepository,
        UserRepository userRepository,
        OperationMapper operationMapper,
        Environment env
    ) {
        this.operationRepository = operationRepository;
        this.movementRepository = movementRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.operationMapper = operationMapper;
        this.env = env;
    }

    @Override
    public PaginatedResponse<OperationDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Operation::getCreatedAt).descending());
        Page<Operation> operations = this.operationRepository.findAll(pageable);
        Page<OperationDto> operationsToShow = new PageImpl<OperationDto>(operations.getContent()
            .stream()
            .map(operation -> this.operationMapper.EntityToDto(operation))
            .toList()
        );
        return DataFormater.paginate(operationsToShow);
    }

    @Override
    public PaginatedResponse<OperationDto> getByAccount(UUID accountId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Operation::getCreatedAt).descending());
        Page<Operation> operations = this.operationRepository.findByOrdererAccountId(accountId, pageable);
        Page<OperationDto> operationsToShow = new PageImpl<OperationDto>(operations.getContent()
            .stream()
            .map(operation -> this.operationMapper.EntityToDto(operation))
            .toList()
        );
        return DataFormater.paginate(operationsToShow);
    }

    @Override
    public PaginatedResponse<OperationDto> getByAuth(int page, int size) {
        String usercode = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByUsercode(usercode)
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Operation::getCreatedAt).descending());
        Page<Operation> operations = this.operationRepository.findByUser(user.getId(), pageable);
        Page<OperationDto> operationsToShow = new PageImpl<OperationDto>(operations.getContent()
            .stream()
            .map(operation -> this.operationMapper.EntityToDto(operation))
            .toList()
        );
        return DataFormater.paginate(operationsToShow);

    }

    @Override
    public PaginatedResponse<OperationDto> getByUser(UUID userId, int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByUser'");
    }

    @Override
    public OperationDto getById(UUID operationId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public OperationDto create(CreateOperationDto createOperationDto) {
        Account userAccount = this.getAccountById(createOperationDto.getAccountId());
        // Check if that account is activated
        if(userAccount.getStatus().equals(AccountStatus.NOT_ACTIVATED)) throw new AccountNotActivatedException("This account is not activated yet.");
        // Check if that account has enough money
        if(userAccount.getBalance().compareTo(createOperationDto.getAmount()) == -1){
            throw new NotEnoughBalanceException("There is not enough money in your account to proceed.");
        }

        Account beneficiaryAccount = null;
        String beneficiaryExternalAccount = "";
        if(createOperationDto.getBeneficiaryAccount().substring(4, 8).equals(this.env.getProperty("BANK.CODE"))){
            beneficiaryAccount = this.getAccountByIban(createOperationDto.getBeneficiaryAccount());
            // Check if that account is activated
            if(beneficiaryAccount.getStatus().equals(AccountStatus.NOT_ACTIVATED)){
                throw new AccountNotActivatedException("This account is not activated yet."); 
            }
        } else {
            beneficiaryExternalAccount = createOperationDto.getBeneficiaryAccount();
        }
        
        Operation operation = new Operation();
        operation.setConcept(createOperationDto.getConcept());
        operation.setOrdererAccount(userAccount);
        operation.setStatus(OperationStatus.DONE);
        if(createOperationDto.getBeneficiaryAccount().substring(4, 8).equals(this.env.getProperty("BANK.CODE"))){
            operation.setCounterpartAccount(beneficiaryAccount);
            operation.setStatus(OperationStatus.DONE);
        } else {
            operation.setCounterpartExternalAccount(beneficiaryExternalAccount);
            operation.setStatus(OperationStatus.PENDING);
        }
        operation.setType(createOperationDto.getOperationType());

        Operation savedOperation = this.operationRepository.save(operation);

        // Creates movement to add money to Beneficiary
        Movement movToBeneficiary = new Movement();
        if(beneficiaryAccount != null) movToBeneficiary.setAccount(beneficiaryAccount);
        if(!beneficiaryExternalAccount.isBlank()) movToBeneficiary.setExternalAccount(beneficiaryExternalAccount);
        movToBeneficiary.setAmount(createOperationDto.getAmount());
        movToBeneficiary.setOperation(savedOperation);
        Movement savedMovToBeneficiary = this.movementRepository.save(movToBeneficiary);

        // Creates movement to extract money from Account. This always be an account from our bank.
        Movement movExtractFromAccount = new Movement();
        movExtractFromAccount.setAccount(userAccount);
        movExtractFromAccount.setAmount(createOperationDto.getAmount().negate());
        movExtractFromAccount.setOperation(savedOperation);
        Movement savedMovExtractFromAccount = this.movementRepository.save(movExtractFromAccount);

        // Adds movements to operation for getting them when asking for operation
        savedOperation.addMovement(savedMovToBeneficiary);
        savedOperation.addMovement(savedMovExtractFromAccount);

        // Update balance for orderer account
        userAccount.setBalance(userAccount.getBalance().subtract(createOperationDto.getAmount()));
        this.accountRepository.save(userAccount);

        // Update balance for beneficiary account if our bank owns it
        if(beneficiaryAccount != null){
            beneficiaryAccount.setBalance(beneficiaryAccount.getBalance().add(createOperationDto.getAmount()));
            this.accountRepository.save(beneficiaryAccount);
        }

        return this.operationMapper.EntityToDto(savedOperation);
    }

    @Override
    public OperationDto update(UUID operationId, UpdateOperationDto updateOperationDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(UUID operationId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    private Account getAccountById(UUID id) {
        return this.accountRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found."));
    }

    private Account getAccountByIban(String iban){
        return this.accountRepository.findByIban(iban)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found."));
    }
    
}
