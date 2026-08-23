package com.jcooldevelopment.easybank_api.controller;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.Operation.CreateOperationAdminDto;
import com.jcooldevelopment.easybank_api.dto.Operation.OperationAdminDto;
import com.jcooldevelopment.easybank_api.dto.Operation.UpdateOperationDto;
import com.jcooldevelopment.easybank_api.service.Operation.OperationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/operation")
@Validated
public class OperationAdminController {

    private final OperationService operationService;

    public OperationAdminController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("")
    public ResponseEntity<Apiresponse<PaginatedResponse<OperationAdminDto>>> getAllOperations(
        @RequestParam(required = false, defaultValue = "1") @Min(value = 1, message = "Page minimal value is 1.") int page,
        @RequestParam(required = false, defaultValue = "10") @Min(value = 1, message = "Page size minimal value is 1.") int size,
        @RequestParam(required = false, defaultValue = "") String concept,
        @RequestParam(required = false, defaultValue = "") String status,
        @RequestParam(required = false, defaultValue = "") String type
    )  {
        PaginatedResponse<OperationAdminDto> operations = this.operationService.getAll(page, size, concept, status, type);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Apiresponse<PaginatedResponse<OperationAdminDto>>("Operations found.", operations));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<OperationAdminDto>> getOperation(@PathVariable UUID id){
        OperationAdminDto operation = this.operationService.getByIdForAdmin(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Apiresponse<OperationAdminDto>("Operation found.", operation));
    }

    @PostMapping("")
    public ResponseEntity<Apiresponse<OperationAdminDto>> postOperation(
        @Valid @RequestBody CreateOperationAdminDto createOperationAdminDto
    ){
        OperationAdminDto operation = this.operationService.createByAdmin(createOperationAdminDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new Apiresponse<OperationAdminDto>("Operation created.", operation));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Apiresponse<OperationAdminDto>> patchOperation(
        @PathVariable UUID id,
        @Valid @RequestBody UpdateOperationDto updateOperationDto
    ){
        OperationAdminDto updatedOperation = this.operationService.update(id, updateOperationDto);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Apiresponse<OperationAdminDto>("Operation updated.", updatedOperation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Apiresponse<Void>> deleteOPeration(@PathVariable UUID id){
        this.operationService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Apiresponse<>("Operation deleted.", null));
    }
}
