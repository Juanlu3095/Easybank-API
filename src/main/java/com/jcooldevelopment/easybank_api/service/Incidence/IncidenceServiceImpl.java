package com.jcooldevelopment.easybank_api.service.Incidence;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.contracts.entity.Incidence;
import com.jcooldevelopment.easybank_api.contracts.entity.IncidenceType;
import com.jcooldevelopment.easybank_api.dto.Incidence.CreateIncidenceDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.IncidenceDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.UpdateIncidenceDto;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.mapper.IncidenceMapper;
import com.jcooldevelopment.easybank_api.repository.IncidenceRepository;
import com.jcooldevelopment.easybank_api.repository.IncidenceTypeRepository;
import com.jcooldevelopment.easybank_api.utils.DataFormater;

@Service
public class IncidenceServiceImpl implements IncidenceService{

    private final IncidenceTypeRepository incidenceTypeRepository;
    private final IncidenceRepository incidenceRepository;
    private final IncidenceMapper incidenceMapper;

    public IncidenceServiceImpl(IncidenceRepository incidenceRepository, IncidenceMapper mapper, IncidenceTypeRepository incidenceTypeRepository) {
        this.incidenceRepository = incidenceRepository;
        this.incidenceMapper = mapper;
        this.incidenceTypeRepository = incidenceTypeRepository;
    }

    @Override
    public PaginatedResponse<IncidenceDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Incidence::getCreatedAt).descending());
        Page<Incidence> incidences = this.incidenceRepository.findAll(pageable);
        Page<IncidenceDto> incidencesToShow = new PageImpl<IncidenceDto>(incidences.getContent() // PageImpl is the implementation of interface Page
            .stream()
            .map(incidence -> incidenceMapper.EntityToDto(incidence))
            .toList());
        PaginatedResponse<IncidenceDto> paginatedResult = DataFormater.paginate(incidencesToShow);
        return paginatedResult;
    }

    @Override
    public IncidenceDto getById(UUID id) {
        Incidence incidence = this.incidenceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Incidence not found."));

        return incidenceMapper.EntityToDto(incidence);
    }

    @Override
    public IncidenceDto create(CreateIncidenceDto createIncidenceDtoDto) {
        IncidenceType incidenceType = incidenceTypeRepository.findById(createIncidenceDtoDto.getIncidence_type())
            .orElseThrow(() -> new ResourceNotFoundException("Incidence type not found."));

        Incidence incidenceToSave = incidenceMapper.CreateIncidenceDtoToEntity(createIncidenceDtoDto);
        incidenceToSave.setCreatedAt(LocalDateTime.now());
        incidenceToSave.setIncidence_type(incidenceType);
        Incidence savedIncidence = incidenceRepository.save(incidenceToSave);
        return incidenceMapper.EntityToDto(savedIncidence);
    }

    @Override
    public IncidenceDto update(UUID id, UpdateIncidenceDto updateIncidenceDto) {
        Incidence incidenceToUpdate = this.incidenceRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Incidence not found."));

        IncidenceType incidenceType = incidenceTypeRepository.findById(updateIncidenceDto.getIncidence_type())
            .orElseThrow(() -> new ResourceNotFoundException("Incidence type not found."));
        
        incidenceToUpdate.setUser_id(updateIncidenceDto.getUser_id());
        incidenceToUpdate.setIncidence_type(incidenceType);
        incidenceToUpdate.setMessage(updateIncidenceDto.getMessage());
        incidenceToUpdate.setStatus(updateIncidenceDto.getStatus());

        Incidence savedIncidence = incidenceRepository.save(incidenceToUpdate);
        return incidenceMapper.EntityToDto(savedIncidence);
    }

    @Override
    public boolean delete(UUID id) {
        Incidence incidenceToDelete = this.incidenceRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Incidence not found."));

        incidenceRepository.delete(incidenceToDelete);
        return true;
    }

}
