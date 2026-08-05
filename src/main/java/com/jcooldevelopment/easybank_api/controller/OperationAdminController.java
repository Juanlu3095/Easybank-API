package com.jcooldevelopment.easybank_api.controller;

import com.jcooldevelopment.easybank_api.service.Operation.OperationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/operation")
@Validated
public class OperationAdminController {

    private final OperationService operationService;

    public OperationAdminController(OperationService operationService) {
        this.operationService = operationService;
    }
}
