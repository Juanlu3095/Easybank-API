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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.Account.AccountDto;
import com.jcooldevelopment.easybank_api.dto.Account.CreateAccountDto;
import com.jcooldevelopment.easybank_api.service.Account.AccountService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/client/account")
@Validated
public class AccountClientController {

    private final AccountService accountService;

    public AccountClientController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("")
    public ResponseEntity<Apiresponse<PaginatedResponse<AccountDto>>> getAccounts(
        @RequestParam(required = false, defaultValue = "1") @Min(value = 1, message = "Page minimal value is 1.") int page, // The page to retrieve, the name of the variable is the same for the url
        @RequestParam(required = false, defaultValue = "10") @Min(value = 1, message = "Page size minimal value is 1.") int size
    ) {
        PaginatedResponse<AccountDto> accounts = this.accountService.getAllByUser(page, size);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new Apiresponse<PaginatedResponse<AccountDto>>("Accounts were found.", accounts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<AccountDto>> getAccount(@PathVariable UUID id) {
        AccountDto account = this.accountService.getById(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new Apiresponse<AccountDto>("Account found.", account));
    }

    @PostMapping("")
    public ResponseEntity<Apiresponse<AccountDto>> postAccount(@Valid @RequestBody CreateAccountDto createAccountDto) {
        AccountDto account = this.accountService.create(createAccountDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .location(URI.create("/api/client/account/" + account.getId()))
            .body(new Apiresponse<AccountDto>("Account created.", account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Apiresponse<Void>> deleteAccount(@PathVariable UUID id) {
        this.accountService.delete(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new Apiresponse<>("Account deleted.", null));
    }
}
