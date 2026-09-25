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
import com.jcooldevelopment.easybank_api.dto.Incidence.CreateIncidenceAdminDto;
import com.jcooldevelopment.easybank_api.dto.Incidence.IncidenceAdminDto;
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
@RequestMapping("/api/admin/incidence")
@Validated
public class IncidenceAdminController {

    private final IncidenceService incidenceService;

    public IncidenceAdminController (IncidenceService service) {
        this.incidenceService = service;
    }

    @GetMapping("")
    public ResponseEntity<Apiresponse<PaginatedResponse<IncidenceAdminDto>>> getIncidences(
        @RequestParam(required = false, defaultValue = "1") @Min(value = 1, message = "Page minimal value is 1.") int page, // The page to retrieve, the name of the variable is the same for the url
        @RequestParam(required = false, defaultValue = "10") @Min(value = 1, message = "Page size minimal value is 1.") int size // The size of data in page
    ) {
        PaginatedResponse<IncidenceAdminDto> incidences = this.incidenceService.getAll(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<PaginatedResponse<IncidenceAdminDto>>("Incidences were found.", incidences));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<IncidenceAdminDto>> getIncidence(@PathVariable UUID id){
        IncidenceAdminDto incidenceDto = this.incidenceService.getByIdForAdmin(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<IncidenceAdminDto>("Incidence found.", incidenceDto));
    }

    @PostMapping("")
    public ResponseEntity<Apiresponse<IncidenceAdminDto>> postIncidence(@Valid @RequestBody CreateIncidenceAdminDto createIncidenceDto) {
        IncidenceAdminDto incidenceSaved = this.incidenceService.createByAdmin(createIncidenceDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .location(URI.create("/api/incidence/" + incidenceSaved.getId())) 
            .body(new Apiresponse<IncidenceAdminDto>("Incidence saved.", incidenceSaved));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Apiresponse<IncidenceAdminDto>> putIncidence(@PathVariable UUID id, @Valid @RequestBody UpdateIncidenceDto updateIncidenceDto) {
        IncidenceAdminDto updatedIncidence = this.incidenceService.update(id, updateIncidenceDto);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Apiresponse<IncidenceAdminDto>("Incidence updated.", updatedIncidence));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Apiresponse<Void>> deleteIncidence(UUID id) {
        this.incidenceService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<Void>("Incidence deleted.", null));
    }
    
}
