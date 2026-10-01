package com.jcooldevelopment.easybank_api.controller;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RestController;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.User.CreateUserDto;
import com.jcooldevelopment.easybank_api.dto.User.UpdateUserDto;
import com.jcooldevelopment.easybank_api.dto.User.UserAdminDto;
import com.jcooldevelopment.easybank_api.dto.User.UserFilterDto;
import com.jcooldevelopment.easybank_api.service.User.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/user")
@Validated
public class UserAdminController {

    private UserService userService;

    public UserAdminController(UserService service) {
        this.userService = service;
    }

    @GetMapping("")
    public ResponseEntity<Apiresponse<PaginatedResponse<UserAdminDto>>> getUsers(
        @Valid UserFilterDto filtersDto
    ) {
        PaginatedResponse<UserAdminDto> users = this.userService.getAll(filtersDto);
        return ResponseEntity.status(HttpStatus.OK).body(
            new Apiresponse<PaginatedResponse<UserAdminDto>>("Users were found.", users)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<UserAdminDto>> getUser(@PathVariable UUID id) {
        UserAdminDto user = this.userService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<UserAdminDto>("User found.", user));
    }

    @PostMapping("")
    public ResponseEntity<Apiresponse<UserAdminDto>> postUser(@Valid @RequestBody CreateUserDto createUserDto) {
        UserAdminDto savedUser = this.userService.create(createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .location(URI.create("/api/user/" + savedUser.getId()))
            .body(new Apiresponse<UserAdminDto>("User created.", savedUser));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Apiresponse<UserAdminDto>> putUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserDto updateUserDto) {
        UserAdminDto updatedUser = this.userService.update(id, updateUserDto);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Apiresponse<UserAdminDto>("User updated.", updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Apiresponse<Void>> deleteMessage(@PathVariable UUID id) {
        this.userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<Void>("User deleted.", null));
    }
}
