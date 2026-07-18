package com.jcooldevelopment.easybank_api.exception;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
