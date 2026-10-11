package com.jcooldevelopment.easybank_api.service.IncidenceType;

import java.util.List;

import com.jcooldevelopment.easybank_api.dto.IncidenceType.CreateIncidenceTypeDto;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.IncidenceTypeDto;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.UpdateIncidenceTypeDto;

public interface IncidenceTypeService {

    public List<IncidenceTypeDto> getAll();

    public IncidenceTypeDto getById(int id);

    public IncidenceTypeDto create(CreateIncidenceTypeDto createIncidenceTypeDto);

    public IncidenceTypeDto update(int id, UpdateIncidenceTypeDto updateIncidenceTypeDto);

    public void delete(int id);
}
