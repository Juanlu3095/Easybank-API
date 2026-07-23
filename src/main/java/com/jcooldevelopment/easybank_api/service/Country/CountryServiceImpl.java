package com.jcooldevelopment.easybank_api.service.Country;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.entity.Country;
import com.jcooldevelopment.easybank_api.dto.Country.CountryDto;
import com.jcooldevelopment.easybank_api.dto.Country.CreateCountryDto;
import com.jcooldevelopment.easybank_api.dto.Country.UpdateCountryDto;
import com.jcooldevelopment.easybank_api.exception.CountryAlreadyExists;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.mapper.CountryMapper;
import com.jcooldevelopment.easybank_api.repository.CountryRepository;

@Service
public class CountryServiceImpl implements CountryService{

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public CountryServiceImpl(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    @Override
    public List<CountryDto> getAll() {
        List<Country> countries = this.countryRepository.findAll();
        return countries.stream()
            .map(country -> this.countryMapper.EntityToDto(country))
            .toList();
    }

    @Override
    public CountryDto getById(int id) {
        Country country = this.countryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Country not found."));
        
        return this.countryMapper.EntityToDto(country);
    }

    @Override
    public CountryDto create(CreateCountryDto createCountryDto) {
        int repeatedCountry = this.countryRepository.countByCode(createCountryDto.getCode());
        if(repeatedCountry > 0) throw new CountryAlreadyExists("This country already exists.");

        Country countryToSave = this.countryMapper.CreateCountryDtoToEntity(createCountryDto);
        Country savedCountry = this.countryRepository.save(countryToSave);
        return countryMapper.EntityToDto(savedCountry);
    }

    @Override
    public CountryDto update(int id, UpdateCountryDto updateCountryDto) {
        Country country = this.countryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Country not found."));

        int repeatedCountry = this.countryRepository.countByCode(updateCountryDto.getCode());

        if(country.getCode().equals(updateCountryDto.getCode()) && repeatedCountry > 1 ||
            !country.getCode().equals(updateCountryDto.getCode()) && repeatedCountry > 0 ) {
            throw new CountryAlreadyExists("This country already exists.");
        }
        
        country.setName(updateCountryDto.getName());
        country.setCode(updateCountryDto.getCode());
        Country savedCountry = this.countryRepository.save(country);
        return this.countryMapper.EntityToDto(savedCountry);
    }

    @Override
    public boolean delete(int id) {
        Country country = this.countryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Country not found."));

        this.countryRepository.delete(country);
        return true;
    }

}
