package com.jcooldevelopment.easybank_api.service.IncidenceType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.contracts.entity.IncidenceType;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.CreateIncidenceTypeDto;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.IncidenceTypeDto;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.UpdateIncidenceTypeDto;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.mapper.IncidenceTypeMapper;
import com.jcooldevelopment.easybank_api.repository.IncidenceTypeRepository;
import com.jcooldevelopment.easybank_api.utils.DataFormater;

@Service
public class IncidenceTypeServiceImpl implements IncidenceTypeService{

    private final IncidenceTypeRepository repository;
    private final IncidenceTypeMapper mapper;

    public IncidenceTypeServiceImpl(IncidenceTypeRepository incidenceTypeRepository, IncidenceTypeMapper incidenceMapper) {
        this.repository = incidenceTypeRepository;
        this.mapper = incidenceMapper;
    }

    @Override
    public PaginatedResponse<IncidenceTypeDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(IncidenceType::getName).descending());
        Page<IncidenceType> IncidenceTypes = this.repository.findAll(pageable);
        Page<IncidenceTypeDto> IncidenceTypesToShow = new PageImpl<IncidenceTypeDto>(IncidenceTypes.getContent() // PageImpl is the implementation of interface Page
            .stream()
            .map(IncidenceType -> mapper.EntityToDto(IncidenceType))
            .toList());
        PaginatedResponse<IncidenceTypeDto> paginatedResult = DataFormater.paginate(IncidenceTypesToShow);
        return paginatedResult;
    }

    @Override
    public IncidenceTypeDto getById(int id) {
        IncidenceType IncidenceType = this.repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Incidence type not found."));

        return mapper.EntityToDto(IncidenceType);
    }

    @Override
    public IncidenceTypeDto create(CreateIncidenceTypeDto incidenceTypeDto) {
        IncidenceType IncidenceTypeToSave = mapper.CreateIncidenceTypeDtoToEntity(incidenceTypeDto);
        IncidenceType savedIncidenceType = repository.save(IncidenceTypeToSave);
        return mapper.EntityToDto(savedIncidenceType);
    }

    @Override
    public IncidenceTypeDto update(int id, UpdateIncidenceTypeDto incidenceTypeDto) {
        IncidenceType incidenceTypeToUpdate = this.repository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Incidence type not found."));
        
        incidenceTypeToUpdate.setName(incidenceTypeDto.getName());
        IncidenceType savedIncidenceType = repository.save(incidenceTypeToUpdate);
        return mapper.EntityToDto(savedIncidenceType);
    }

    @Override
    public boolean delete(int id) {
        IncidenceType IncidenceTypeToDelete = this.repository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Incidence type not found."));

        repository.delete(IncidenceTypeToDelete);
        return true;
    }

}
