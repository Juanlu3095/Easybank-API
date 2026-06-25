package com.jcooldevelopment.easybank_api.service.IncidenceType;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.CreateIncidenceTypeDto;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.IncidenceTypeDto;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.UpdateIncidenceTypeDto;

public interface IncidenceTypeService {

    public PaginatedResponse<IncidenceTypeDto> getAll(int page, int size);

    public IncidenceTypeDto getById(int id);

    public IncidenceTypeDto create(CreateIncidenceTypeDto message);

    public IncidenceTypeDto update(int id, UpdateIncidenceTypeDto message);

    public boolean delete(int id);
}
