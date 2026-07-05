package com.jcooldevelopment.easybank_api.exception;

public class DniAlreadyExistsException extends RuntimeException{
    public DniAlreadyExistsException(String message) {
        super(message);
    }
}
