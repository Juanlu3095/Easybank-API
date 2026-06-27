package com.jcooldevelopment.easybank_api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.Incidence;
import com.jcooldevelopment.easybank_api.dto.Incidence.CreateIncidenceDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.IncidenceDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.UpdateIncidenceDto;

@Component
public class IncidenceMapper {
    private final ModelMapper modelMapper;
    
    public IncidenceMapper (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Incidence CreateIncidenceDtoToEntity(CreateIncidenceDto createIncidenceDto) {
       return modelMapper.map(createIncidenceDto, Incidence.class);
    }

    public Incidence UpdateIncidenceDtoToEntity(UpdateIncidenceDto updateIncidenceDto) {
       return modelMapper.map(updateIncidenceDto, Incidence.class);
    }

    public IncidenceDto EntityToDto(Incidence incidence) {
        return modelMapper.map(incidence, IncidenceDto.class);
    }
}
