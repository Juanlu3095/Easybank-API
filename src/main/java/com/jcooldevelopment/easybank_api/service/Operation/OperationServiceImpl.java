package com.jcooldevelopment.easybank_api.service.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.contracts.entity.Account;
import com.jcooldevelopment.easybank_api.contracts.entity.Movement;
import com.jcooldevelopment.easybank_api.contracts.entity.Operation;
import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.contracts.enums.AccountPurpose;
import com.jcooldevelopment.easybank_api.contracts.enums.OperationStatus;
import com.jcooldevelopment.easybank_api.contracts.enums.OperationType;
import com.jcooldevelopment.easybank_api.dto.Movement.MovementPerOperationOnlyIban;
import com.jcooldevelopment.easybank_api.dto.Operation.CreateOperationAdminDto;
import com.jcooldevelopment.easybank_api.dto.Operation.CreateOperationDto;
import com.jcooldevelopment.easybank_api.dto.Operation.OperationAdminDto;
import com.jcooldevelopment.easybank_api.dto.Operation.OperationDto;
import com.jcooldevelopment.easybank_api.dto.Operation.UpdateOperationDto;
import com.jcooldevelopment.easybank_api.exception.AccountNotActivatedException;
import com.jcooldevelopment.easybank_api.exception.AccountPurposeNotValid;
import com.jcooldevelopment.easybank_api.exception.NotEnoughBalanceException;
import com.jcooldevelopment.easybank_api.exception.OrdererAndBeneficiaryCannotBeSameException;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.exception.UserNotAuthorizedException;
import com.jcooldevelopment.easybank_api.mapper.MovementMapper;
import com.jcooldevelopment.easybank_api.mapper.OperationMapper;
import com.jcooldevelopment.easybank_api.projections.operation.OperationProjection;
import com.jcooldevelopment.easybank_api.projections.operation.OperationProjectionWithAccount;
import com.jcooldevelopment.easybank_api.repository.AccountRepository;
import com.jcooldevelopment.easybank_api.repository.MovementRepository;
import com.jcooldevelopment.easybank_api.repository.OperationRepository;
import com.jcooldevelopment.easybank_api.repository.UserRepository;
import com.jcooldevelopment.easybank_api.specs.operation.OperationSpecs;
import com.jcooldevelopment.easybank_api.utils.DataFormater;

@Service
public class OperationServiceImpl implements OperationService{

    private final OperationRepository operationRepository;
    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final OperationMapper operationMapper;
    private final MovementMapper movementMapper;
    private final Environment env;

    public OperationServiceImpl(
        OperationRepository operationRepository,
        MovementRepository movementRepository,
        AccountRepository accountRepository,
        UserRepository userRepository,
        OperationMapper operationMapper,
        MovementMapper movementMapper,
        Environment env
    ) {
        this.operationRepository = operationRepository;
        this.movementRepository = movementRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.operationMapper = operationMapper;
        this.movementMapper = movementMapper;
        this.env = env;
    }

    @Override
    public PaginatedResponse<OperationAdminDto> getAll(
        int page,
        int size,
        String concept, 
        String status,
        String type,
        String ordererIban,
        String counterpartIban
    ) {
        Specification<Operation> filters = Specification
            .where(OperationSpecs.findByConcept(concept))
            .and(OperationSpecs.findByStatus(status))
            .and(OperationSpecs.findByType(type))
            .and(OperationSpecs.findByOrdererIban(ordererIban))
            .and(OperationSpecs.findByCounterpartIban(counterpartIban, this.env.getProperty("BANK.CODE")));
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Operation::getCreatedAt).descending());
        Page<Operation> operations = this.operationRepository.findAll(filters, pageable);
        Page<OperationAdminDto> operationsToShow = operations.map(operation ->
            this.operationMapper.EntityToAdminDto(operation)
        );
        return DataFormater.paginate(operationsToShow);
    }

    private Map<UUID, List<MovementPerOperationOnlyIban>> getMovementsByOperations (Page<OperationProjection> operations){
        List<UUID> uuids = new ArrayList<>();
        for (OperationProjection operation : operations.getContent()) {
            uuids.add(operation.id());
        }
        
        List<MovementPerOperationOnlyIban> movements = this.movementRepository.findByOperationIds(uuids)
            .stream()
            .map(movement -> this.movementMapper.MovementProjectionToMovementOnlyIban(movement))
            .toList();
        
        // Create a map of movements and then group by operationId
        // https://www.arquitecturajava.com/java-list-to-map-y-collectors/
        return movements.stream()
            .collect(Collectors.groupingBy(movement -> movement.getOperationId()));
    }

    @Override
    public PaginatedResponse<OperationDto> getByAccount(UUID accountId, int page, int size) {
        // Check if account belongs to authenticated user
        String usercode = SecurityContextHolder.getContext().getAuthentication().getName();
        if(this.accountRepository.accountBelongsToUser(accountId, usercode) < 1){
            throw new UserNotAuthorizedException("User has no authorization to access the account data");
        }

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Operation::getCreatedAt).descending());
        Page<OperationProjection> operations = this.operationRepository.findByAccountIdWithProjection(accountId, pageable);

        // Create a map of movements and then group by operationId
        Map<UUID, List<MovementPerOperationOnlyIban>> movementsByOperation = this.getMovementsByOperations(operations);
        
        Page<OperationDto> operationsDto = operations.map(operation -> {
            OperationDto operationDto = this.operationMapper.projectionToDto(operation);
            List<MovementPerOperationOnlyIban> movementsToAdd = movementsByOperation.get(operationDto.getId());
            movementsToAdd.forEach(movement -> operationDto.addMovement(movement));
            return operationDto;
        });

        return DataFormater.paginate(operationsDto);
    }

    @Override
    public PaginatedResponse<OperationDto> getByAuth(int page, int size) {
        String usercode = SecurityContextHolder.getContext().getAuthentication().getName();

        Pageable pageable = PageRequest.of(page - 1, size); // Sort in custom sql query not here, it creates problems
        Page<OperationProjection> operations = this.operationRepository.findByUserWithProjection(usercode, pageable);

        // Create a map of movements and then group by operationId
        Map<UUID, List<MovementPerOperationOnlyIban>> movementsByOperation = this.getMovementsByOperations(operations);

        Page<OperationDto> operationsDto = operations.map(operation -> {
            OperationDto operationDto = this.operationMapper.projectionToDto(operation);
            List<MovementPerOperationOnlyIban> movementsToAdd = movementsByOperation.get(operationDto.getId());
            movementsToAdd.forEach(movement -> operationDto.addMovement(movement));
            return operationDto;
        });

        return DataFormater.paginate(operationsDto);

    }

    @Override
    public PaginatedResponse<OperationAdminDto> getByUser(UUID userId, int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByUser'");
    }

    @Override
    public OperationDto getById(UUID operationId) {
        // Check if operation belongs to authenticated user
        String usercode = SecurityContextHolder.getContext().getAuthentication().getName();
        int operationBelongsToUser = this.operationRepository.operationBelongsToUser(operationId, usercode);

        if (operationBelongsToUser < 1){
            throw new UserNotAuthorizedException("User has no authorization to access this operation.");
        }

        // Find operation by UUID
        OperationProjection operationProjection = this.operationRepository.findByIdAsProjection(operationId)
            .orElseThrow(()-> new ResourceNotFoundException("Operation not found."));
        
        // Search the movements by operationId, creating a List with only one uuid
        List<MovementPerOperationOnlyIban> movements = this.movementRepository.findByOperationIds(List.of(operationId))
            .stream()
            .map(movement -> this.movementMapper.MovementProjectionToMovementOnlyIban(movement))
            .toList();

        // Transforms operation projection to DTO and adds its movements
        OperationDto operation = this.operationMapper.projectionToDto(operationProjection);
        movements.forEach(movement -> operation.addMovement(movement));
        return operation;
    }

    public OperationAdminDto getByIdForAdmin(UUID id){
        OperationProjectionWithAccount operation = this.operationRepository.findByIdAsProjectionForAdmin(id)
            .orElseThrow(() -> new ResourceNotFoundException("Operation not found."));
        
        return this.operationMapper.projectionToAdminDto(operation);
    }

    /**
     * Validates activation, if enough amount in account, if it belongs to user and if beneficiary and orderer of an
     * operation are not the same.
     * @param account The entity account from DB
     * @param createOperationDto The DTO from the HTTP request
     * @param usercode Usercode in users table
     */
    private void validateAccountToUse(Account account, CreateOperationDto createOperationDto, String usercode){
        // Checks if that account is activated
        if(!account.isActivated()) throw new AccountNotActivatedException("This account is not activated yet.");
        // Checks if that account has enough money
        if(account.getBalance().compareTo(createOperationDto.getAmount()) == -1){
            throw new NotEnoughBalanceException("There is not enough money in your account to proceed.");
        }
        // Checks if orderer and beneficiary accounts are not the same
        if(account.getIban().equals(createOperationDto.getBeneficiaryAccount())){
            throw new OrdererAndBeneficiaryCannotBeSameException("Orderer and beneficiary IBAN accounts cannot be the same.");
        }
        // Checks if account belongs to user;
        if(this.accountRepository.accountBelongsToUser(account.getId(), usercode) < 1){
            throw new UserNotAuthorizedException("User has no authorization to access the account data");
        }
    }

    /**
     * Validates activation, if enough amount in account, if it belongs to user and if beneficiary and orderer of an
     * operation are not the same.
     * @param account The entity account from DB
     * @param createOperationDto The DTO for admin from the HTTP request
     * @param usercode Usercode in users table
     */
    private void validateAdminAccountToUse(Account account, CreateOperationAdminDto createOperationDto, String usercode){
        // Checks if that account is activated
        if(!account.isActivated()) throw new AccountNotActivatedException("This account is not activated yet.");
        // Checks if that account has enough money
        if(account.getBalance().compareTo(createOperationDto.getAmount()) == -1){
            throw new NotEnoughBalanceException("There is not enough money in your account to proceed.");
        }
        // Checks if orderer and beneficiary accounts are not the same
        if(account.getIban().equals(createOperationDto.getBeneficiaryAccount())){
            throw new OrdererAndBeneficiaryCannotBeSameException("Orderer and beneficiary IBAN accounts cannot be the same.");
        }
        // Checks if account belongs to user; must verify if user has admin role, any admin can use BALANCE_ADJUSTMENT accounts
        // if(this.accountRepository.accountBelongsToUser(account.getId(), usercode) < 1){
        //     throw new UserNotAuthorizedException("User has no authorization to access the account data");
        // }
        // Checks if account is internal for adjustments
        if(!account.getAccountPurpose().equals(AccountPurpose.BALANCE_ADJUSTMENT)){
            throw new AccountPurposeNotValid("This account is not valid for internal purposes.");
        }
    }

    @Override
    public OperationDto create(CreateOperationDto createOperationDto) {
        Account userAccount = this.getAccountById(createOperationDto.getAccountId());
        String usercode = SecurityContextHolder.getContext().getAuthentication().getName();
        this.validateAccountToUse(userAccount, createOperationDto, usercode);

        Account beneficiaryAccount = null;
        String beneficiaryExternalAccount = "";
        if(createOperationDto.getBeneficiaryAccount().substring(4, 8).equals(this.env.getProperty("BANK.CODE"))){
            beneficiaryAccount = this.getAccountByIban(createOperationDto.getBeneficiaryAccount());
            // Check if that account is activated
            if(!beneficiaryAccount.isActivated()){
                throw new AccountNotActivatedException("This account is not activated yet."); 
            }
        } else {
            beneficiaryExternalAccount = createOperationDto.getBeneficiaryAccount();
        }

        User orderer = this.userRepository.findByUsercode(usercode)
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        
        Operation operation = new Operation();
        operation.setConcept(createOperationDto.getConcept());
        operation.setOrderer(orderer);
        operation.setOrdererAccount(userAccount);
        if(createOperationDto.getBeneficiaryAccount().substring(4, 8).equals(this.env.getProperty("BANK.CODE"))){
            operation.setCounterpartAccount(beneficiaryAccount);
            operation.setStatus(OperationStatus.DONE);
        } else {
            operation.setCounterpartExternalAccount(beneficiaryExternalAccount);
            operation.setStatus(OperationStatus.PENDING);
        }

        // Checks operation type: if both accounts belongs to user, it is a transfer.
        // The orderer account is obviously one which belongs to user.
        if(this.accountRepository.accountBelongsToUserByIban(
            createOperationDto.getBeneficiaryAccount(),
            usercode
        ) == 1) {
            operation.setType(OperationType.TRANSFER);
        } else if (createOperationDto.getOperationType().equals(OperationType.PAYMENT.toString())){
            operation.setType(OperationType.PAYMENT);
        } else {
            operation.setType(OperationType.MONEY_TRANSFER);
        }

        Operation savedOperation = this.operationRepository.save(operation);

        // Creates movement to add money to Beneficiary
        Movement movToBeneficiary = new Movement();
        if(beneficiaryAccount != null) movToBeneficiary.setAccount(beneficiaryAccount);
        if(!beneficiaryExternalAccount.isBlank()) movToBeneficiary.setExternalAccount(beneficiaryExternalAccount);
        movToBeneficiary.setAmount(createOperationDto.getAmount());
        movToBeneficiary.setOperation(savedOperation);

        // Creates movement to extract money from Account. This always be an account from our bank.
        Movement movExtractFromAccount = new Movement();
        movExtractFromAccount.setAccount(userAccount);
        movExtractFromAccount.setAmount(createOperationDto.getAmount().negate());
        movExtractFromAccount.setOperation(savedOperation);

        List<Movement> movements = new ArrayList<>();
        movements.add(movToBeneficiary);
        movements.add(movExtractFromAccount);
        List<Movement> savedMovements = this.movementRepository.saveAll(movements);

        // Adds movements to operation for getting them when asking for operation
        savedMovements.forEach(movement -> savedOperation.addMovement(movement));

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
    public OperationAdminDto createByAdmin(CreateOperationAdminDto createOperationDto){
        Account userAccount = this.getAccountById(createOperationDto.getAccountId());
        String usercode = SecurityContextHolder.getContext().getAuthentication().getName();
        this.validateAdminAccountToUse(userAccount, createOperationDto, usercode);

        Account beneficiaryAccount = null;
        String beneficiaryExternalAccount = "";
        // Check if beneficiary account belongs to our bank
        if(createOperationDto.getBeneficiaryAccount().substring(4, 8).equals(this.env.getProperty("BANK.CODE"))){
            beneficiaryAccount = this.getAccountByIban(createOperationDto.getBeneficiaryAccount());
            // Check if that account is activated
            if(!beneficiaryAccount.isActivated()){
                throw new AccountNotActivatedException("This account is not activated yet."); 
            }
        } else {
            beneficiaryExternalAccount = createOperationDto.getBeneficiaryAccount();
        }

        User orderer = this.userRepository.findByUsercode(usercode)
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Operation operation = new Operation();
        operation.setConcept(createOperationDto.getConcept());
        operation.setOrderer(orderer);
        operation.setOrdererAccount(userAccount);
        operation.setStatus(OperationStatus.DONE);
        if(createOperationDto.getBeneficiaryAccount().substring(4, 8).equals(this.env.getProperty("BANK.CODE"))){
            operation.setCounterpartAccount(beneficiaryAccount);
            operation.setStatus(OperationStatus.DONE);
        } else {
            operation.setCounterpartExternalAccount(beneficiaryExternalAccount);
            operation.setStatus(OperationStatus.PENDING);
        }

        // Admin only creates operations to fix errors in operations
        operation.setType(OperationType.BALANCE_ADJUSTMENT);

        Operation savedOperation = this.operationRepository.save(operation);

        // Creates movement to add money to Beneficiary
        Movement movToBeneficiary = new Movement();
        if(beneficiaryAccount != null) movToBeneficiary.setAccount(beneficiaryAccount);
        if(!beneficiaryExternalAccount.isBlank()) movToBeneficiary.setExternalAccount(beneficiaryExternalAccount);
        movToBeneficiary.setAmount(createOperationDto.getAmount());
        movToBeneficiary.setOperation(savedOperation);

        // Creates movement to extract money from Account. This always be an account from our bank.
        Movement movExtractFromAccount = new Movement();
        movExtractFromAccount.setAccount(userAccount);
        movExtractFromAccount.setAmount(createOperationDto.getAmount().negate());
        movExtractFromAccount.setOperation(savedOperation);

        List<Movement> movements = new ArrayList<>();
        movements.add(movToBeneficiary);
        movements.add(movExtractFromAccount);
        List<Movement> savedMovements = this.movementRepository.saveAll(movements);

        // Adds movements to operation for getting them when asking for operation
        savedMovements.forEach(movement -> savedOperation.addMovement(movement));

        // Update balance for orderer account
        userAccount.setBalance(userAccount.getBalance().subtract(createOperationDto.getAmount()));
        this.accountRepository.save(userAccount);

        // Update balance for beneficiary account if our bank owns it
        if(beneficiaryAccount != null){
            beneficiaryAccount.setBalance(beneficiaryAccount.getBalance().add(createOperationDto.getAmount()));
            this.accountRepository.save(beneficiaryAccount);
        }

        return this.operationMapper.EntityToAdminDto(savedOperation);
    }

    @Override
    public OperationAdminDto update(UUID operationId, UpdateOperationDto updateOperationDto) {
        Operation operation = this.operationRepository.findById(operationId)
            .orElseThrow(() -> new ResourceNotFoundException("Operation not found."));

        operation.setType(updateOperationDto.getOperationType());
        operation.setStatus(updateOperationDto.getStatus());
        Operation updatedOperation = this.operationRepository.save(operation);
        return this.operationMapper.EntityToAdminDto(updatedOperation);
    }

    @Override
    public void delete(UUID operationId) {
        Operation operation = this.operationRepository.findById(operationId)
            .orElseThrow(() -> new ResourceNotFoundException("Operation not found."));

        this.movementRepository.deleteAll(operation.getMovements());
        
        this.operationRepository.delete(operation);
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
