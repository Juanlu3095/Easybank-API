package com.jcooldevelopment.easybank_api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.Operation;
import com.jcooldevelopment.easybank_api.dto.Operation.CreateOperationDto;
import com.jcooldevelopment.easybank_api.dto.Operation.OperationDto;
import com.jcooldevelopment.easybank_api.dto.Operation.UpdateOperationDto;

@Component
public class OperationMapper {

    private final ModelMapper modelMapper;

    public OperationMapper (ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public Operation CreateOperationDtoToEntity(CreateOperationDto createOperationDto) {
       return modelMapper.map(createOperationDto, Operation.class);
    }

    public Operation UpdateOperationDtoToEntity(UpdateOperationDto updateOperationDto) {
       return modelMapper.map(updateOperationDto, Operation.class);
    }

    public OperationDto EntityToDto(Operation Operation) {
        return modelMapper.map(Operation, OperationDto.class);
    }
}
