package com.jcooldevelopment.easybank_api.exception;

public class AccountPurposeNotValid extends RuntimeException{
    public AccountPurposeNotValid(String message){
        super(message);
    }
}
