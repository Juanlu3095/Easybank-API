package com.jcooldevelopment.easybank_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.service.ActivationCode.ActivationCodeServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/activate")
public class ActivationCodeController {

    private final ActivationCodeServiceImpl activationCodeService;

    public ActivationCodeController(ActivationCodeServiceImpl activationCodeService) {
        this.activationCodeService = activationCodeService;
    }

    @PostMapping("/{code}")
    public ResponseEntity<Apiresponse<Void>> activateUser(
        @RequestParam(required = true) String code
    ) {
        boolean result = this.activationCodeService.enableUser(code);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<>("User enabled.", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Apiresponse<>("User could not be enabled.", null));
        }
    }
    

}
