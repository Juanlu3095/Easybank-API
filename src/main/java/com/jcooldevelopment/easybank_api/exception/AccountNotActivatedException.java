package com.jcooldevelopment.easybank_api.exception;

public class AccountNotActivatedException extends RuntimeException{

    public AccountNotActivatedException(String message){
        super(message);
    }
}
