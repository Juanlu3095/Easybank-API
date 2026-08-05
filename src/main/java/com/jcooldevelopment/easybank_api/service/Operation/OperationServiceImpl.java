package com.jcooldevelopment.easybank_api.service.Operation;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.Operation.CreateOperationDto;
import com.jcooldevelopment.easybank_api.dto.Operation.OperationDto;
import com.jcooldevelopment.easybank_api.dto.Operation.UpdateOperationDto;
import com.jcooldevelopment.easybank_api.repository.MovementRepository;
import com.jcooldevelopment.easybank_api.repository.OperationRepository;

@Service
public class OperationServiceImpl implements OperationService{

    private final OperationRepository operationRepository;
    private final MovementRepository movementRepository;
    
    public OperationServiceImpl(OperationRepository operationRepository, MovementRepository movementRepository) {
        this.operationRepository = operationRepository;
        this.movementRepository = movementRepository;
    }

    @Override
    public PaginatedResponse<OperationDto> getAll(int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public PaginatedResponse<OperationDto> getByAccount(UUID accountId, int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByAccount'");
    }

    @Override
    public PaginatedResponse<OperationDto> getByAuth(int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByAuth'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
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

    
}
