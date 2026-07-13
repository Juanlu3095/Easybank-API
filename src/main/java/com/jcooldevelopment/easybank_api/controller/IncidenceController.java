package com.jcooldevelopment.easybank_api.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.Incidence.CreateIncidenceDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.IncidenceDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.UpdateIncidenceDto;
import com.jcooldevelopment.easybank_api.service.Incidence.IncidenceService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/incidence")
@Validated
public class IncidenceController {

    private final IncidenceService incidenceService;

    public IncidenceController (IncidenceService service) {
        this.incidenceService = service;
    }

    @GetMapping("")
    public ResponseEntity<Apiresponse<PaginatedResponse<IncidenceDto>>> getIncidences(
        @RequestParam(required = false, defaultValue = "1") @Min(value = 1, message = "Page minimal value is 1.") int page, // The page to retrieve, the name of the variable is the same for the url
        @RequestParam(required = false, defaultValue = "10") @Min(value = 1, message = "Page size minimal value is 1.") int size // The size of data in page
    ) {
        PaginatedResponse<IncidenceDto> incidences = this.incidenceService.getAll(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<PaginatedResponse<IncidenceDto>>("Incidences were found.", incidences));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<IncidenceDto>> getIncidence(@PathVariable UUID id){
        IncidenceDto incidenceDto = this.incidenceService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<IncidenceDto>("Incidence found.", incidenceDto));
    }

    @PostMapping("")
    public ResponseEntity<Apiresponse<IncidenceDto>> postMessage(@Valid @RequestBody CreateIncidenceDto createIncidenceDto) {
        IncidenceDto incidenceSaved = this.incidenceService.create(createIncidenceDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .location(URI.create("/api/incidence/" + incidenceSaved.getId())) 
            .body(new Apiresponse<IncidenceDto>("Incidence saved.", incidenceSaved));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Apiresponse<IncidenceDto>> putMessage(@PathVariable UUID id, @Valid @RequestBody UpdateIncidenceDto updateIncidenceDto) {
        IncidenceDto updatedIncidence = this.incidenceService.update(id, updateIncidenceDto);
        return ResponseEntity.status(HttpStatus.OK)
            .location(URI.create("/api/incidence/" + updatedIncidence.getId()))
            .body(new Apiresponse<IncidenceDto>("Incidence updated.", updatedIncidence));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Apiresponse<Void>> deleteMessage(UUID id) {
        boolean result = this.incidenceService.delete(id);
        if(!result) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new Apiresponse<>("Service unavailable.", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<Void>("Incidence deleted.", null));
    }
    
}
