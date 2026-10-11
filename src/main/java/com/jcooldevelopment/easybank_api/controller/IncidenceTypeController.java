package com.jcooldevelopment.easybank_api.controller;

import java.net.URI;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.CreateIncidenceTypeDto;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.IncidenceTypeDto;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.UpdateIncidenceTypeDto;
import com.jcooldevelopment.easybank_api.service.IncidenceType.IncidenceTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/incidencetype")
@Validated // Need this to use @Min for RequestParam: https://www.codejava.net/frameworks/spring-boot/rest-api-validate-query-parameters-examples
public class IncidenceTypeController {

    private final IncidenceTypeService incidenceTypeService;

    public IncidenceTypeController(IncidenceTypeService incidenceTypeService) {
        this.incidenceTypeService = incidenceTypeService;
    }

    // We suppose there is not to many incidences types, thus, we don't use pagination. Also, they are going to be used in select input
    // For RequestParams validation: https://docs.hibernate.org/validator/5.1/reference/en-US/html/chapter-message-interpolation.html#section-interpolation-with-message-expressions
    @GetMapping("")
    public ResponseEntity<Apiresponse<List<IncidenceTypeDto>>> getIncidenceTypes() {
        List<IncidenceTypeDto> incidenceTypes = this.incidenceTypeService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<List<IncidenceTypeDto>>("Incidence types were found.", incidenceTypes));
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
            .body(new Apiresponse<IncidenceTypeDto>("Incidence type updated.", updatedIncidenceType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Apiresponse<Void>> deleteMessage(int id) {
        this.incidenceTypeService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<Void>("Incidence type deleted.", null));
    }
}
