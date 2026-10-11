package com.jcooldevelopment.easybank_api.service.IncidenceType;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.entity.IncidenceType;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.CreateIncidenceTypeDto;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.IncidenceTypeDto;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.UpdateIncidenceTypeDto;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.mapper.IncidenceTypeMapper;
import com.jcooldevelopment.easybank_api.repository.IncidenceTypeRepository;

@Service
public class IncidenceTypeServiceImpl implements IncidenceTypeService{

    private final IncidenceTypeRepository incidenceTypeRepository;
    private final IncidenceTypeMapper incidenceTypeMapper;

    public IncidenceTypeServiceImpl(IncidenceTypeRepository incidenceTypeRepository, IncidenceTypeMapper incidenceTypeMapper) {
        this.incidenceTypeRepository = incidenceTypeRepository;
        this.incidenceTypeMapper = incidenceTypeMapper;
    }

    @Override
    public List<IncidenceTypeDto> getAll() {
        List<IncidenceType> incidenceTypes = this.incidenceTypeRepository.findAll();
        return incidenceTypes.stream()
            .map(incidenceType ->
                this.incidenceTypeMapper.EntityToDto(incidenceType)
            ).toList(); // This list in unmodifiable
    }

    @Override
    public IncidenceTypeDto getById(int id) {
        IncidenceType IncidenceType = this.incidenceTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Incidence type not found."));

        return incidenceTypeMapper.EntityToDto(IncidenceType);
    }

    @Override
    public IncidenceTypeDto create(CreateIncidenceTypeDto incidenceTypeDto) {
        IncidenceType IncidenceTypeToSave = incidenceTypeMapper.CreateIncidenceTypeDtoToEntity(incidenceTypeDto);
        IncidenceType savedIncidenceType = incidenceTypeRepository.save(IncidenceTypeToSave);
        return incidenceTypeMapper.EntityToDto(savedIncidenceType);
    }

    @Override
    public IncidenceTypeDto update(int id, UpdateIncidenceTypeDto incidenceTypeDto) {
        IncidenceType incidenceTypeToUpdate = this.incidenceTypeRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Incidence type not found."));
        
        incidenceTypeToUpdate.setName(incidenceTypeDto.getName());
        IncidenceType savedIncidenceType = incidenceTypeRepository.save(incidenceTypeToUpdate);
        return incidenceTypeMapper.EntityToDto(savedIncidenceType);
    }

    @Override
    public void delete(int id) {
        IncidenceType IncidenceTypeToDelete = this.incidenceTypeRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Incidence type not found."));

        incidenceTypeRepository.delete(IncidenceTypeToDelete);
    }

}
