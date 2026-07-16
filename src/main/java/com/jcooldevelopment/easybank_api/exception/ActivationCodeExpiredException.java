package com.jcooldevelopment.easybank_api.exception;

public class ActivationCodeExpiredException extends RuntimeException{
    public ActivationCodeExpiredException(String message) {
        super(message);
    }
}
