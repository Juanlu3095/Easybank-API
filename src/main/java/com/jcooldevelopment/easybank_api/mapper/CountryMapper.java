package com.jcooldevelopment.easybank_api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.Country;
import com.jcooldevelopment.easybank_api.dto.Country.CountryDto;
import com.jcooldevelopment.easybank_api.dto.Country.CreateCountryDto;
import com.jcooldevelopment.easybank_api.dto.Country.UpdateCountryDto;

@Component
public class CountryMapper {

    private final ModelMapper modelMapper;

    public CountryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public Country CreateCountryDtoToEntity(CreateCountryDto createCountryDto) {
       return modelMapper.map(createCountryDto, Country.class);
    }

    public Country UpdateCountryDtoToEntity(UpdateCountryDto updateCountryDto) {
       return modelMapper.map(updateCountryDto, Country.class);
    }

    public CountryDto EntityToDto(Country Country) {
        return modelMapper.map(Country, CountryDto.class);
    }
}
