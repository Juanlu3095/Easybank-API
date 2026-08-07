package com.jcooldevelopment.easybank_api.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.Operation;
import com.jcooldevelopment.easybank_api.dto.Movement.MovementPerOperationDto;
import com.jcooldevelopment.easybank_api.dto.Operation.CreateOperationDto;
import com.jcooldevelopment.easybank_api.dto.Operation.OperationDto;
import com.jcooldevelopment.easybank_api.dto.Operation.UpdateOperationDto;

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

    public OperationDto EntityToDto(Operation operation) {
        OperationDto operationDto = new OperationDto();
        operationDto.setId(operation.getId());
        operationDto.setConcept(operation.getConcept());
        operationDto.setStatus(operation.getStatus());
        operationDto.setType(operation.getType());
        operationDto.setOrdererAccount(operation.getOrdererAccount());
        operationDto.setCounterpartAccount(operation.getCounterpartAccount());
        operationDto.setCounterpartExternalAccount(operation.getCounterpartExternalAccount());
        operationDto.setCreatedAt(operation.getCreatedAt());
        operationDto.setUpdatedAt(operation.getUpdatedAt());

        List<MovementPerOperationDto> movementDtos = new ArrayList<>();
        operation.getMovements().forEach(movement -> {
            operationDto.addMovement(this.movementMapper.EntityToMovementPerOperationDto(movement));
        });
        operationDto.setMovements(movementDtos);

        return operationDto;
    }
}
