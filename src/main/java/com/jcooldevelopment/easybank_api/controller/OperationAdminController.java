package com.jcooldevelopment.easybank_api.controller;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.Operation.OperationAdminDto;
import com.jcooldevelopment.easybank_api.service.Operation.OperationService;

import jakarta.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
        @RequestParam(required = false, defaultValue = "10") @Min(value = 1, message = "Page size minimal value is 1.") int size
    )  {
        PaginatedResponse<OperationAdminDto> operations = this.operationService.getAll(page, size);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Apiresponse<PaginatedResponse<OperationAdminDto>>("Operations found.", operations));
    }
}
