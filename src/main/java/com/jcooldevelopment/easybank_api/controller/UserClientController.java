package com.jcooldevelopment.easybank_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.dto.User.CreatePinDto;
import com.jcooldevelopment.easybank_api.dto.User.UserDto;
import com.jcooldevelopment.easybank_api.service.User.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/client/user")
@Validated
public class UserClientController {

    private UserService userService;

    public UserClientController(UserService service) {
        this.userService = service;
    }

    @GetMapping("")
    public ResponseEntity<Apiresponse<UserDto>> getUser() {
        UserDto user = this.userService.getByAuth();
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<UserDto>("User's data found.", user));
    }

    @PostMapping("/pin")
    public ResponseEntity<Apiresponse<Void>> createPin(@Valid @RequestBody CreatePinDto createPinDto){
        this.userService.setPin(createPinDto);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<>("User pin set successfully.", null));
    }
}
