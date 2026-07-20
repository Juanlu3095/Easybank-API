package com.jcooldevelopment.easybank_api.exception;

public class ResetPasswordExpiredException extends RuntimeException{
    public ResetPasswordExpiredException(String message) {
        super(message);
    }
}
