package com.jcooldevelopment.easybank_api.service.Incidence;

import java.util.UUID;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.Incidence.CreateIncidenceDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.IncidenceDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.UpdateIncidenceDto;

public interface IncidenceService {

    public PaginatedResponse<IncidenceDto> getAll(int page, int size);

    public IncidenceDto getById(UUID id);

    public IncidenceDto create(CreateIncidenceDto createIncidenceDtoDto);

    public IncidenceDto update(UUID id, UpdateIncidenceDto updateIncidenceDto);

    public boolean delete(UUID id);
}
