package com.jcooldevelopment.easybank_api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.Movement;
import com.jcooldevelopment.easybank_api.dto.Movement.MovementDto;

@Component
public class MovementMapper {

    private final ModelMapper modelMapper;

    public MovementMapper (ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public MovementDto EntityToDto(Movement movement) {
        return this.modelMapper.map(movement, MovementDto.class);
    }

}
