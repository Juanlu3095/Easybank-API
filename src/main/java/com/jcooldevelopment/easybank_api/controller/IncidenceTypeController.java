package com.jcooldevelopment.easybank_api.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.CreateIncidenceTypeDto;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.IncidenceTypeDto;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.UpdateIncidenceTypeDto;
import com.jcooldevelopment.easybank_api.service.IncidenceType.IncidenceTypeService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/incidencetype")
@Validated // Need this to use @Min for RequestParam: https://www.codejava.net/frameworks/spring-boot/rest-api-validate-query-parameters-examples
public class IncidenceTypeController {

    private final IncidenceTypeService incidenceTypeService;

    public IncidenceTypeController(IncidenceTypeService incidenceTypeService) {
        this.incidenceTypeService = incidenceTypeService;
    }

    // For RequestParams validation: https://docs.hibernate.org/validator/5.1/reference/en-US/html/chapter-message-interpolation.html#section-interpolation-with-message-expressions
    @GetMapping("")
    public ResponseEntity<Apiresponse<PaginatedResponse<IncidenceTypeDto>>> getIncidenceTypes(
        @RequestParam(required = false, defaultValue = "1") @Min(value = 1, message = "Page minimal value is 1.") int page, // The page to retrieve, the name of the variable is the same for the url
        @RequestParam(required = false, defaultValue = "10") @Min(value = 1, message = "Page size minimal value is 1.") int size // The size of data in page
    ) {
        PaginatedResponse<IncidenceTypeDto> incidenceTypes = this.incidenceTypeService.getAll(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<PaginatedResponse<IncidenceTypeDto>>("Incidence types were found.", incidenceTypes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<IncidenceTypeDto>> getIncidenceType(@PathVariable int id){
        IncidenceTypeDto incidenceTypeDto = this.incidenceTypeService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<IncidenceTypeDto>("Incidence type found.", incidenceTypeDto));
    }

    @PostMapping("")
    public ResponseEntity<Apiresponse<IncidenceTypeDto>> postMessage(@Valid @RequestBody CreateIncidenceTypeDto createIncidenceTypeDto) {
        IncidenceTypeDto incidenceTypeSaved = this.incidenceTypeService.create(createIncidenceTypeDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .location(URI.create("/api/incidencetype/" + incidenceTypeSaved.getId())) 
            .body(new Apiresponse<IncidenceTypeDto>("Incidence type saved.", incidenceTypeSaved));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Apiresponse<IncidenceTypeDto>> putMessage(@PathVariable int id, @Valid @RequestBody UpdateIncidenceTypeDto updateIncidenceTypeDto) {
        IncidenceTypeDto updatedIncidenceType = this.incidenceTypeService.update(id, updateIncidenceTypeDto);
        return ResponseEntity.status(HttpStatus.OK)
            .location(URI.create("/api/incidencetype/" + updatedIncidenceType.getId()))
            .body(new Apiresponse<IncidenceTypeDto>("Incidence type updated.", updatedIncidenceType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Apiresponse<Void>> deleteMessage(int id) {
        boolean result = this.incidenceTypeService.delete(id);
        if(!result) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new Apiresponse<>("Service unavailable.", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<Void>("Incidence type deleted.", null));
    }
}
