package com.jcooldevelopment.easybank_api.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.dto.AccountType.AccountTypeDto;
import com.jcooldevelopment.easybank_api.dto.AccountType.CreateAccountTypeDto;
import com.jcooldevelopment.easybank_api.dto.AccountType.UpdateAccountTypeDto;
import com.jcooldevelopment.easybank_api.service.AccountType.AccountTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounttype")
public class AccountTypeController {

    private final AccountTypeService accountTypeService;

    public AccountTypeController(AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @GetMapping("")
    public ResponseEntity<Apiresponse<List<AccountTypeDto>>> getAccountTypes(){
        List<AccountTypeDto> accountTypes = this.accountTypeService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<List<AccountTypeDto>>("Account types found.", accountTypes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<AccountTypeDto>> getAccountType(@PathVariable UUID id){
        AccountTypeDto accountTypeDto = this.accountTypeService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<AccountTypeDto>("Account type found.", accountTypeDto));
    }

    @PostMapping("")
    public ResponseEntity<Apiresponse<AccountTypeDto>> postMessage(@Valid @RequestBody CreateAccountTypeDto createAccountTypeDto) {
        AccountTypeDto accountTypeSaved = this.accountTypeService.create(createAccountTypeDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .location(URI.create("/api/accounttype/" + accountTypeSaved.getId())) 
            .body(new Apiresponse<AccountTypeDto>("Account type saved.", accountTypeSaved));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Apiresponse<AccountTypeDto>> putMessage(@PathVariable UUID id, @Valid @RequestBody UpdateAccountTypeDto updateAccountTypeDto) {
        AccountTypeDto updatedAccountType = this.accountTypeService.update(id, updateAccountTypeDto);
        return ResponseEntity.status(HttpStatus.OK)
            .location(URI.create("/api/Accounttype/" + updatedAccountType.getId()))
            .body(new Apiresponse<AccountTypeDto>("Account type updated.", updatedAccountType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Apiresponse<Void>> deleteMessage(UUID id) {
        boolean result = this.accountTypeService.delete(id);
        if(!result) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new Apiresponse<>("Service unavailable.", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<Void>("Account type deleted.", null));
    }
}
