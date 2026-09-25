package com.jcooldevelopment.easybank_api.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.Incidence.CreateIncidenceDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.IncidenceDto;
import com.jcooldevelopment.easybank_api.service.Incidence.IncidenceService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/client/incidence")
@Validated
public class IncidenceClientController {

    private final IncidenceService incidenceService;

    public IncidenceClientController (IncidenceService service) {
        this.incidenceService = service;
    }

    @GetMapping("")
    public ResponseEntity<Apiresponse<PaginatedResponse<IncidenceDto>>> getIncidences(
        @RequestParam(required = false, defaultValue = "1") @Min(value = 1, message = "Page minimal value is 1.") int page, // The page to retrieve, the name of the variable is the same for the url
        @RequestParam(required = false, defaultValue = "10") @Min(value = 1, message = "Page size minimal value is 1.") int size // The size of data in page
    ) {
        PaginatedResponse<IncidenceDto> incidences = this.incidenceService.getByAuth(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<PaginatedResponse<IncidenceDto>>("Incidences were found.", incidences));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<IncidenceDto>> getIncidence(@PathVariable UUID id){
        IncidenceDto incidenceDto = this.incidenceService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<IncidenceDto>("Incidence found.", incidenceDto));
    }

    @PostMapping("")
    public ResponseEntity<Apiresponse<IncidenceDto>> postIncidence(@Valid @RequestBody CreateIncidenceDto createIncidenceDto) {
        IncidenceDto incidenceSaved = this.incidenceService.create(createIncidenceDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .location(URI.create("/api/incidence/" + incidenceSaved.getId())) 
            .body(new Apiresponse<IncidenceDto>("Incidence saved.", incidenceSaved));
    }
}
