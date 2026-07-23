package com.jcooldevelopment.easybank_api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.dto.Country.CountryDto;
import com.jcooldevelopment.easybank_api.dto.Country.CreateCountryDto;
import com.jcooldevelopment.easybank_api.dto.Country.UpdateCountryDto;
import com.jcooldevelopment.easybank_api.service.Country.CountryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/country")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("")
    public ResponseEntity<Apiresponse<List<CountryDto>>> getCountries() {
        List<CountryDto> countries = this.countryService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<List<CountryDto>>("Countries were found.", countries));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<CountryDto>> getCountry(@PathVariable int id){
        CountryDto countryDto = this.countryService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<CountryDto>("Country found.", countryDto));
    }

    @PostMapping("")
    public ResponseEntity<Apiresponse<CountryDto>> postCountry(@Valid @RequestBody CreateCountryDto createCountryDto) {
        CountryDto countrySaved = this.countryService.create(createCountryDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .location(URI.create("/api/Country/" + countrySaved.getId())) 
            .body(new Apiresponse<CountryDto>("Country saved.", countrySaved));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Apiresponse<CountryDto>> putCountry(@PathVariable int id, @Valid @RequestBody UpdateCountryDto updateCountryDto) {
        CountryDto updatedCountry = this.countryService.update(id, updateCountryDto);
        return ResponseEntity.status(HttpStatus.OK)
            .location(URI.create("/api/Country/" + updatedCountry.getId()))
            .body(new Apiresponse<CountryDto>("Country updated.", updatedCountry));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Apiresponse<Void>> deleteCountry(int id) {
        boolean result = this.countryService.delete(id);
        if(!result) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new Apiresponse<>("Service unavailable.", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<Void>("Country deleted.", null));
    }
}
