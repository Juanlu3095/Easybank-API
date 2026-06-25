package com.jcooldevelopment.easybank_api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.IncidenceType;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.CreateIncidenceTypeDto;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.IncidenceTypeDto;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.UpdateIncidenceTypeDto;

@Component
public class IncidenceTypeMapper {
    private final ModelMapper modelMapper;
    
    public IncidenceTypeMapper (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // https://modelmapper.org/getting-started/
    public IncidenceType CreateIncidenceTypeDtoToEntity(CreateIncidenceTypeDto createIncidenceTypeDto) {
       return modelMapper.map(createIncidenceTypeDto, IncidenceType.class);
    }

    public IncidenceType UpdateIncidenceTypeDtoToEntity(UpdateIncidenceTypeDto updateIncidenceTypeDto) {
       return modelMapper.map(updateIncidenceTypeDto, IncidenceType.class);
    }

    public IncidenceTypeDto EntityToDto(IncidenceType IncidenceType) {
        return modelMapper.map(IncidenceType, IncidenceTypeDto.class);
    }
}
