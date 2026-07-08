package com.jcooldevelopment.easybank_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.dto.Auth.LoginDto;
import com.jcooldevelopment.easybank_api.dto.Auth.RegisterDto;
import com.jcooldevelopment.easybank_api.service.Auth.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Apiresponse<String>> login(@Valid @RequestBody LoginDto userLogin) {
        String token = this.authService.login(userLogin);
        if (token.isBlank()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new Apiresponse<String>("There is a problem in our system. Please contact our support service.", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<String>("Login successful.", token));
    }

    @PostMapping("/register")
    public ResponseEntity<Apiresponse<Void>> register(@Valid @RequestBody RegisterDto userRegister) {
        boolean result = this.authService.register(userRegister);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<>("Register successful.", null));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<>("Register failed.", null));
        }
        
    }
    
}
