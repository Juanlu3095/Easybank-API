package com.jcooldevelopment.easybank_api.service.Incidence;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.contracts.entity.Incidence;
import com.jcooldevelopment.easybank_api.contracts.entity.IncidenceType;
import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.contracts.enums.IncidenceStatus;
import com.jcooldevelopment.easybank_api.dto.Incidence.CreateIncidenceDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.IncidenceDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.UpdateIncidenceDto;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.mapper.IncidenceMapper;
import com.jcooldevelopment.easybank_api.repository.IncidenceRepository;
import com.jcooldevelopment.easybank_api.repository.IncidenceTypeRepository;
import com.jcooldevelopment.easybank_api.repository.UserRepository;
import com.jcooldevelopment.easybank_api.utils.DataFormater;

@Service
public class IncidenceServiceImpl implements IncidenceService{

    private final IncidenceTypeRepository incidenceTypeRepository;
    private final IncidenceRepository incidenceRepository;
    private final IncidenceMapper incidenceMapper;
    private final UserRepository userRepository;

    public IncidenceServiceImpl(IncidenceRepository incidenceRepository,
        IncidenceMapper mapper,
        IncidenceTypeRepository incidenceTypeRepository,
        UserRepository userRepository
    ) {
        this.incidenceRepository = incidenceRepository;
        this.incidenceMapper = mapper;
        this.incidenceTypeRepository = incidenceTypeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PaginatedResponse<IncidenceDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Incidence::getCreatedAt).descending());
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

        // Get usercode from SecurityContextHolder, which is updated in JwtAuthFilter
        String usercode = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByUsercode(usercode)
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        
        Incidence incidenceToSave = incidenceMapper.CreateIncidenceDtoToEntity(createIncidenceDtoDto);
        incidenceToSave.setUser_id(user);
        incidenceToSave.setCreatedAt(LocalDateTime.now());
        incidenceToSave.setUpdatedAt(LocalDateTime.now());
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
        
        String usercode = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByUsercode(usercode)
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        incidenceToUpdate.setUser_id(user); // Must get User_id from JWT
        incidenceToUpdate.setIncidence_type(incidenceType);
        incidenceToUpdate.setMessage(updateIncidenceDto.getMessage());
        incidenceToUpdate.setStatus(IncidenceStatus.valueOf(updateIncidenceDto.getStatus())); // Valueof to obtain contraint value in enum
        incidenceToUpdate.setUpdatedAt(LocalDateTime.now());

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
