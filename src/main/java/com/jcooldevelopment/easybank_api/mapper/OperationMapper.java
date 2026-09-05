package com.jcooldevelopment.easybank_api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.Operation;
import com.jcooldevelopment.easybank_api.contracts.enums.OperationStatus;
import com.jcooldevelopment.easybank_api.contracts.enums.OperationType;
import com.jcooldevelopment.easybank_api.dto.Account.AccountDtoNoUsers;
import com.jcooldevelopment.easybank_api.dto.Operation.CreateOperationDto;
import com.jcooldevelopment.easybank_api.dto.Operation.OperationAdminDto;
import com.jcooldevelopment.easybank_api.dto.Operation.OperationDto;
import com.jcooldevelopment.easybank_api.dto.Operation.UpdateOperationDto;
import com.jcooldevelopment.easybank_api.projections.operation.OperationProjection;
import com.jcooldevelopment.easybank_api.projections.operation.OperationProjectionWithAccount;

@Component
public class OperationMapper {

    private final ModelMapper modelMapper;
    private final MovementMapper movementMapper;

    public OperationMapper (ModelMapper modelMapper, MovementMapper movementMapper){
        this.modelMapper = modelMapper;
        this.movementMapper = movementMapper;
    }

    public Operation CreateOperationDtoToEntity(CreateOperationDto createOperationDto) {
       return modelMapper.map(createOperationDto, Operation.class);
    }

    public Operation UpdateOperationDtoToEntity(UpdateOperationDto updateOperationDto) {
       return modelMapper.map(updateOperationDto, Operation.class);
    }

    public OperationDto EntityToDto(Operation operation){
        OperationDto operationDto = new OperationDto();
        operationDto.setId(operation.getId());
        operationDto.setConcept(operation.getConcept());
        operationDto.setCounterpartAccountIban(operation.getCounterpartExternalAccount());
        operationDto.setOrderer(operation.getOrderer().getName() + " " + operation.getOrderer().getSurname());
        operationDto.setOrdererAccountIban(operation.getCounterpartAccount().getIban());
        operationDto.setStatus(operation.getStatus());
        operationDto.setType(operation.getType());
        operationDto.setCreatedAt(operation.getCreatedAt());
        operationDto.setUpdatedAt(operation.getUpdatedAt());

        operation.getMovements().forEach(movement -> {
            operationDto.addMovement(movementMapper.EntityToMovementOnlyIban(movement));
        });

        return operationDto;
    }

    public OperationAdminDto EntityToAdminDto(Operation operation) {
        AccountDtoNoUsers ordererAccount = new AccountDtoNoUsers();
        ordererAccount.setId(operation.getOrdererAccount().getId());
        ordererAccount.setIban(operation.getOrdererAccount().getIban());
        ordererAccount.setBicSwift(operation.getOrdererAccount().getBicSwift());
        ordererAccount.setPlace(operation.getOrdererAccount().getBranch().getName());

        AccountDtoNoUsers beneficiaryAccount = null;
        String externalBeneficiaryAccount = null;

        if(operation.getCounterpartAccount() != null) {
            beneficiaryAccount = new AccountDtoNoUsers();
            beneficiaryAccount.setId(operation.getCounterpartAccount().getId());
            beneficiaryAccount.setIban(operation.getCounterpartAccount().getIban());
            beneficiaryAccount.setBicSwift(operation.getCounterpartAccount().getBicSwift());
            beneficiaryAccount.setPlace(operation.getCounterpartAccount().getBranch().getName());
        } else {
            externalBeneficiaryAccount = operation.getCounterpartExternalAccount();
        }

        OperationAdminDto operationDto = new OperationAdminDto();
        operationDto.setId(operation.getId());
        operationDto.setConcept(operation.getConcept());
        operationDto.setStatus(operation.getStatus());
        operationDto.setType(operation.getType());
        operationDto.setOrderer(operation.getOrderer().getName() + " " + operation.getOrderer().getSurname());
        operationDto.setOrdererAccount(ordererAccount);
        operationDto.setCounterpartAccount(beneficiaryAccount);
        operationDto.setCounterpartExternalAccount(externalBeneficiaryAccount);
        operationDto.setCreatedAt(operation.getCreatedAt());
        operationDto.setUpdatedAt(operation.getUpdatedAt());

        operation.getMovements().forEach(movement -> {
            operationDto.addMovement(this.movementMapper.EntityToMovementPerOperationDto(movement));
        });

        return operationDto;
    }

    public OperationAdminDto projectionToAdminDto(OperationProjectionWithAccount operationProjection){
        AccountDtoNoUsers ordererAccount = new AccountDtoNoUsers();
        ordererAccount.setId(operationProjection.ordererAccountId());
        ordererAccount.setIban(operationProjection.ordererAccountIban());
        ordererAccount.setBicSwift(operationProjection.ordererAccountBicswift());
        ordererAccount.setPlace(operationProjection.ordererAccountPlace());

        AccountDtoNoUsers counterpartAccount = new AccountDtoNoUsers();
        counterpartAccount.setId(operationProjection.counterpartAccountId());
        counterpartAccount.setIban(operationProjection.counterpartAccountIban());
        counterpartAccount.setBicSwift(operationProjection.counterpartAccountBicswift());
        counterpartAccount.setPlace(operationProjection.counterpartAccountPlace());

        OperationAdminDto operationDto = new OperationAdminDto();
        operationDto.setId(operationProjection.id());
        operationDto.setConcept(operationProjection.concept());
        operationDto.setStatus(OperationStatus.valueOf(operationProjection.status()));
        operationDto.setType(OperationType.valueOf(operationProjection.type()));
        operationDto.setOrderer(operationProjection.ordererName() + " " + operationProjection.ordererSurname());
        operationDto.setOrdererAccount(ordererAccount);
        operationDto.setCounterpartAccount(counterpartAccount);
        operationDto.setCounterpartExternalAccount(operationProjection.counterpartExternalAccountIban());
        operationDto.setCreatedAt(operationProjection.createdAt());
        operationDto.setUpdatedAt(operationProjection.updatedAt());
        // movements

        return operationDto;
    }

    public OperationDto projectionToDto(OperationProjection operationProjection){
        OperationDto operationDto = new OperationDto();
        operationDto.setId(operationProjection.id());
        operationDto.setConcept(operationProjection.concept());
        operationDto.setStatus(OperationStatus.valueOf(operationProjection.status()));
        operationDto.setType(OperationType.valueOf(operationProjection.type()));
        operationDto.setOrdererAccountIban(operationProjection.ordererAccountIban());
        operationDto.setOrderer(operationProjection.ordererName() + " " + operationProjection.ordererSurname());

        if(operationProjection.counterpartAccountIban() != null){
            operationDto.setCounterpartAccountIban(operationProjection.counterpartAccountIban());
        } else {
            operationDto.setCounterpartAccountIban(operationProjection.counterpartExternalAccountIban());
        }

        operationDto.setCreatedAt(operationProjection.createdAt());
        operationDto.setUpdatedAt(operationProjection.updatedAt());

        return operationDto;
    }
}
