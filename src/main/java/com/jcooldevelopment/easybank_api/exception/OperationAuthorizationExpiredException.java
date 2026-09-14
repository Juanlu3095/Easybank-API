package com.jcooldevelopment.easybank_api.exception;

public class OperationAuthorizationExpiredException extends RuntimeException{
    public OperationAuthorizationExpiredException(String message){
        super(message);
    }
}
