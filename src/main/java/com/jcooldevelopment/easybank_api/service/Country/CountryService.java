package com.jcooldevelopment.easybank_api.service.Country;

import java.util.List;

import com.jcooldevelopment.easybank_api.dto.Country.CountryDto;
import com.jcooldevelopment.easybank_api.dto.Country.CreateCountryDto;
import com.jcooldevelopment.easybank_api.dto.Country.UpdateCountryDto;

public interface CountryService {

    List<CountryDto> getAll();

    CountryDto getById(int id);

    CountryDto create(CreateCountryDto createCountryDto);

    CountryDto update(int id, UpdateCountryDto updateCountryDto);

    boolean delete(int id);
}
