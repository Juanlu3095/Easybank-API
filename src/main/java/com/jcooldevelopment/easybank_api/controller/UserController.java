package com.jcooldevelopment.easybank_api.controller;

import java.net.URI;
import java.util.UUID;

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
import com.jcooldevelopment.easybank_api.dto.User.CreateUserDto;
import com.jcooldevelopment.easybank_api.dto.User.UpdateUserDto;
import com.jcooldevelopment.easybank_api.dto.User.UserDto;
import com.jcooldevelopment.easybank_api.service.User.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    private UserService userService;

    public UserController(UserService service) {
        this.userService = service;
    }

    @GetMapping("")
    public ResponseEntity<Apiresponse<PaginatedResponse<UserDto>>> getUsers(
        @RequestParam(required = false, defaultValue = "1") @Min(value = 1, message = "Page minimal value is 1.") int page, // The page to retrieve, the name of the variable is the same for the url
        @RequestParam(required = false, defaultValue = "10") @Min(value = 1, message = "Page size minimal value is 1.") int size // The size of data in page
    ) {
        PaginatedResponse<UserDto> users = this.userService.getAll(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<PaginatedResponse<UserDto>>("Users were found.", users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<UserDto>> getUser(@PathVariable UUID id) {
        UserDto user = this.userService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<UserDto>("User found.", user));
    }

    @PostMapping("")
    public ResponseEntity<Apiresponse<UserDto>> postUser(@Valid @RequestBody CreateUserDto createUserDto) {
        UserDto savedUser = this.userService.create(createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .location(URI.create("/api/user/" + savedUser.getId()))
            .body(new Apiresponse<UserDto>("User created.", savedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apiresponse<UserDto>> putUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserDto updateUserDto) {
        UserDto updatedUser = this.userService.update(id, updateUserDto);
        return ResponseEntity.status(HttpStatus.OK)
            .location(URI.create("/api/user/" + updatedUser.getId()))
            .body(new Apiresponse<UserDto>("User updated.", updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Apiresponse<Void>> deleteMessage(UUID id) {
        boolean result = this.userService.delete(id);
        if(!result) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new Apiresponse<>("Service unavailable.", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<Void>("User deleted.", null));
    }
}
