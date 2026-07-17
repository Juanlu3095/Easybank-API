package com.jcooldevelopment.easybank_api.exception;

public class UserAlreadyEnabledException extends RuntimeException{
    public UserAlreadyEnabledException(String message) {
        super(message);
    }
}
