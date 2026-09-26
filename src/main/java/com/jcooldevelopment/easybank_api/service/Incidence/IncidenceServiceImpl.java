package com.jcooldevelopment.easybank_api.service.Incidence;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.contracts.entity.Incidence;
import com.jcooldevelopment.easybank_api.contracts.entity.IncidenceType;
import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.contracts.enums.IncidenceStatus;
import com.jcooldevelopment.easybank_api.dto.Incidence.CreateIncidenceAdminDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.CreateIncidenceDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.IncidenceAdminDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.IncidenceDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.UpdateIncidenceDto;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.exception.UserNotAuthorizedException;
import com.jcooldevelopment.easybank_api.mapper.IncidenceMapper;
import com.jcooldevelopment.easybank_api.projections.incidence.IncidenceAdminProjection;
import com.jcooldevelopment.easybank_api.projections.incidence.IncidenceProjection;
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
    public PaginatedResponse<IncidenceAdminDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<IncidenceAdminProjection> incidences = this.incidenceRepository.findAllAsProjection(pageable);
        Page<IncidenceAdminDto> incidencesToShow = incidences.map(incidence ->
            this.incidenceMapper.ProjectionToAdminDto(incidence)
        );
        PaginatedResponse<IncidenceAdminDto> paginatedResult = DataFormater.paginate(incidencesToShow);
        return paginatedResult;
    }

    @Override 
    public PaginatedResponse<IncidenceDto> getByAuth(int page, int size){
        // Careful here, sorting must be done in repository not here when using native query otherwise Hibernate will
        // make mistakes.
        Pageable pageable = PageRequest.of(page - 1, size);
        // Obtain all incidences using usercode in SecurityContextHolder
        String usercode = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<IncidenceProjection> incidences = this.incidenceRepository.findByUsercode(usercode, pageable);
        Page<IncidenceDto> incidencesToShow = incidences.map(incidence ->
            this.incidenceMapper.ProjectionToDto(incidence)
        );
        return DataFormater.paginate(incidencesToShow);
    }

    @Override
    public IncidenceDto getById(UUID id) {
        String usercode = SecurityContextHolder.getContext().getAuthentication().getName();
        Incidence incidence = this.incidenceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Incidence not found."));

        // Verify if incidence belongs to authenticated user
        int getByUsercodeAndId = this.incidenceRepository.incidenceBelongsToUser(id, usercode);

        if(getByUsercodeAndId < 1){
            throw new UserNotAuthorizedException("User not authorized to get access to this operation.");
        }

        return incidenceMapper.EntityToDto(incidence);
    }

    @Override
    public IncidenceAdminDto getByIdForAdmin(UUID id){
        Incidence incidence = this.incidenceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Incidence not found."));

        return incidenceMapper.EntityToAdminDto(incidence);
    }

    @Override
    public IncidenceDto create(CreateIncidenceDto createIncidenceDto) {
        IncidenceType incidenceType = incidenceTypeRepository.findById(createIncidenceDto.getIncidence_type())
            .orElseThrow(() -> new ResourceNotFoundException("Incidence type not found."));

        // Get usercode from SecurityContextHolder, which is updated in JwtAuthFilter
        String usercode = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByUsercode(usercode)
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        UUID savedIncidenceId = incidenceRepository.saveIncidenceAndReturnId(
            user.getId(),
            incidenceType.getId(),
            createIncidenceDto.getMessage(),
            IncidenceStatus.IN_PROCESS.toString()
        );
        Incidence savedIncidence = this.incidenceRepository.findById(savedIncidenceId)
            .orElseThrow(() -> new ResourceNotFoundException("Incidence could not be saved."));
        return incidenceMapper.EntityToDto(savedIncidence);
    }

    @Override
    public IncidenceAdminDto createByAdmin(CreateIncidenceAdminDto createIncidenceDtoDto){
        IncidenceType incidenceType = incidenceTypeRepository.findById(createIncidenceDtoDto.getIncidence_type())
            .orElseThrow(() -> new ResourceNotFoundException("Incidence type not found."));

        // Get user with given id in DTO
        User user = this.userRepository.findById(createIncidenceDtoDto.getUser_id())
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        
        Incidence incidenceToSave = incidenceMapper.CreateIncidenceAdminDtoToEntity(createIncidenceDtoDto);
        incidenceToSave.setUser_id(user);
        incidenceToSave.setCreatedAt(LocalDateTime.now());
        incidenceToSave.setUpdatedAt(LocalDateTime.now());
        incidenceToSave.setIncidence_type(incidenceType);
        Incidence savedIncidence = incidenceRepository.save(incidenceToSave);
        return incidenceMapper.EntityToAdminDto(savedIncidence);
    }

    @Override
    public IncidenceAdminDto update(UUID id, UpdateIncidenceDto updateIncidenceDto) {
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
        return incidenceMapper.EntityToAdminDto(savedIncidence);
    }

    @Override
    public void delete(UUID id) {
        Incidence incidenceToDelete = this.incidenceRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Incidence not found."));

        incidenceRepository.delete(incidenceToDelete);
    }

}
