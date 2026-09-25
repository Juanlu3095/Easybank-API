package com.jcooldevelopment.easybank_api.service.Incidence;

import java.util.UUID;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.Incidence.CreateIncidenceAdminDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.CreateIncidenceDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.IncidenceAdminDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.IncidenceDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.UpdateIncidenceDto;

public interface IncidenceService {

    /**
     * Obtains all incidences with pagination. For admin role.
     * @param page The number of page to see.
     * @param size The size of each page with results.
     * @return Incidences DTO with pagination for admin.
     */
    public PaginatedResponse<IncidenceAdminDto> getAll(int page, int size);

    /**
     * Obtains all incidences that belongs to user with pagination using JWT. For client role.
     * @param page The number of page to see.
     * @param size The size of each page with results.
     * @return Incidences DTO with pagination
     */
    public PaginatedResponse<IncidenceDto> getByAuth(int page, int size);

    /**
     * Obtains one specific incidence by id, previous verification of user belonging. For client role.
     * @param id Incidence's identificator in UUID format.
     * @return Incidence DTO.
     */
    public IncidenceDto getById(UUID id);

    /**
     * Obtains one specific incidence by id. For admin role.
     * @param id Incidence's identificator in UUID format.
     * @return Incidence DTO for admin.
     */
    public IncidenceAdminDto getByIdForAdmin(UUID id);

    /**
     * Creates a new incidence with user credentials (JWT). For client role.
     * @param createIncidenceDtoDto The indicence to create.
     * @return The created incidence as DTO.
     */
    public IncidenceDto create(CreateIncidenceDto createIncidenceDtoDto);

    /**
     * Creates a new incidence. For admin role.
     * @param CreateIncidenceAdminDto The indicence to create.
     * @return The created incidence as DTO for admin.
     */
    public IncidenceAdminDto createByAdmin(CreateIncidenceAdminDto createIncidenceDtoDto);

    /**
     * Updates incidence by given id. Only for admin.
     * @param id The incidence identificator in UUID format.
     * @param updateIncidenceDto The data to update.
     * @return The updated incidence as DTO for admin.
     */
    public IncidenceAdminDto update(UUID id, UpdateIncidenceDto updateIncidenceDto);

    /**
     * Deletes an incidence by id. Only for admin.
     * @param id The incidence identificator in UUID format.
     */
    public void delete(UUID id);
}
