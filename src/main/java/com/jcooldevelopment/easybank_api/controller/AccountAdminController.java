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
import com.jcooldevelopment.easybank_api.dto.Account.AccountAdminDto;
import com.jcooldevelopment.easybank_api.dto.Account.CreateAccountAdminDto;
import com.jcooldevelopment.easybank_api.dto.Account.UpdateAccountAdminDto;
import com.jcooldevelopment.easybank_api.service.Account.AccountService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/admin/account")
@Validated
public class AccountAdminController {

    private final AccountService accountService;

    public AccountAdminController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("")
    public ResponseEntity<Apiresponse<PaginatedResponse<AccountAdminDto>>> getAccounts(
        @RequestParam(required = false, defaultValue = "1") @Min(value = 1, message = "Page minimal value is 1.") int page, // The page to retrieve, the name of the variable is the same for the url
        @RequestParam(required = false, defaultValue = "10") @Min(value = 1, message = "Page size minimal value is 1.") int size
    ) {
        PaginatedResponse<AccountAdminDto> accounts = this.accountService.getAll(page, size);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new Apiresponse<PaginatedResponse<AccountAdminDto>>("Accounts were found.", accounts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<AccountAdminDto>> getAccount(@PathVariable UUID id) {
        AccountAdminDto account = this.accountService.getByIdForAdmin(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new Apiresponse<AccountAdminDto>("Account found.", account));
    }

    @PostMapping("")
    public ResponseEntity<Apiresponse<AccountAdminDto>> postAccount(@Valid @RequestBody CreateAccountAdminDto createAccountDto) {
        AccountAdminDto account = this.accountService.createByAdmin(createAccountDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .location(URI.create("/api/admin/account/" + account.getId()))
            .body(new Apiresponse<AccountAdminDto>("Account created.", account));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apiresponse<AccountAdminDto>> putAccount(@PathVariable UUID id, @Valid @RequestBody UpdateAccountAdminDto updateAccountDto){
        AccountAdminDto account = this.accountService.updateByAdmin(id, updateAccountDto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .location(URI.create("/api/admin/account/" + account.getId()))
            .body(new Apiresponse<AccountAdminDto>("Account updated.", account));   
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Apiresponse<Void>> deleteAccount(@PathVariable UUID id) {
        this.accountService.delete(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new Apiresponse<>("Account deleted.", null));
    }
}
