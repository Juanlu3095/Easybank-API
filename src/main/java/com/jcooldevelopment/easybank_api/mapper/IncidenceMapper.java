package com.jcooldevelopment.easybank_api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.Incidence;
import com.jcooldevelopment.easybank_api.contracts.entity.IncidenceType;
import com.jcooldevelopment.easybank_api.contracts.enums.IncidenceStatus;
import com.jcooldevelopment.easybank_api.dto.Incidence.CreateIncidenceAdminDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.CreateIncidenceDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.IncidenceAdminDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.IncidenceDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.UpdateIncidenceDto;
import com.jcooldevelopment.easybank_api.projections.incidence.IncidenceProjection;

@Component
public class IncidenceMapper {
    private final ModelMapper modelMapper;
    
    public IncidenceMapper (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Incidence CreateIncidenceDtoToEntity(CreateIncidenceDto createIncidenceDto) {
       return modelMapper.map(createIncidenceDto, Incidence.class);
    }

    public Incidence CreateIncidenceAdminDtoToEntity(CreateIncidenceAdminDto createIncidenceAdminDto) {
       return modelMapper.map(createIncidenceAdminDto, Incidence.class);
    }

    public Incidence UpdateIncidenceDtoToEntity(UpdateIncidenceDto updateIncidenceDto) {
       return modelMapper.map(updateIncidenceDto, Incidence.class);
    }

    public IncidenceDto EntityToDto(Incidence incidence) {
        return modelMapper.map(incidence, IncidenceDto.class);
    }

    public IncidenceAdminDto EntityToAdminDto(Incidence incidence){
        return modelMapper.map(incidence, IncidenceAdminDto.class);
    }

    public IncidenceDto ProjectionToDto(IncidenceProjection incidenceProjection) {
        IncidenceDto incidenceDto = new IncidenceDto();
        incidenceDto.setId(incidenceProjection.id());
        incidenceDto.setCreatedAt(incidenceProjection.created_at());
        incidenceDto.setIncidence_type(
            new IncidenceType(
                incidenceProjection.incidenceTypeId(),
                incidenceProjection.incidenceType()
            )
        );
        incidenceDto.setMessage(incidenceProjection.message());
        incidenceDto.setStatus(IncidenceStatus.valueOf(incidenceProjection.status()));
        incidenceDto.setUpdatedAt(incidenceProjection.updated_at());
        return incidenceDto;
    }
}
