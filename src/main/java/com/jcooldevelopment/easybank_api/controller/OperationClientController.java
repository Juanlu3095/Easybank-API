package com.jcooldevelopment.easybank_api.controller;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.Operation.CreateOperationDto;
import com.jcooldevelopment.easybank_api.dto.Operation.OperationDto;
import com.jcooldevelopment.easybank_api.service.Operation.OperationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/client/operation")
@Validated
public class OperationClientController {

    private final OperationService operationService;

    public OperationClientController(OperationService operationService){
        this.operationService = operationService;
    }

    @GetMapping("")
    public ResponseEntity<Apiresponse<PaginatedResponse<OperationDto>>> getAllOperations(
        @RequestParam(required = false, defaultValue = "1") @Min(value = 1, message = "Page minimal value is 1.") int page,
        @RequestParam(required = false, defaultValue = "10") @Min(value = 1, message = "Page size minimal value is 1.") int size
    )  {
        PaginatedResponse<OperationDto> operations = this.operationService.getByAuth(page, size);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Apiresponse<PaginatedResponse<OperationDto>>("Operations found.", operations));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<OperationDto>> getOperation(@PathVariable UUID id){
        OperationDto operation = this.operationService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Apiresponse<OperationDto>("Operation found.", operation));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<Apiresponse<PaginatedResponse<OperationDto>>> getOperationsByAccount(
        @PathVariable UUID accountId,
        @RequestParam(required = false, defaultValue = "1") @Min(value = 1, message = "Page minimal value is 1.") int page,
        @RequestParam(required = false, defaultValue = "10") @Min(value = 1, message = "Page size minimal value is 1.") int size
    ){
        PaginatedResponse<OperationDto> operations = this.operationService.getByAccount(accountId, page, size);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Apiresponse<PaginatedResponse<OperationDto>>("Operations found.", operations));
    }

    @PostMapping("")
    public ResponseEntity<Apiresponse<OperationDto>> createOperation(@Valid @RequestBody CreateOperationDto createOperationDto){
        OperationDto createdOperation = this.operationService.create(createOperationDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new Apiresponse<OperationDto>("Operation created.", createdOperation));
    }
    
}
