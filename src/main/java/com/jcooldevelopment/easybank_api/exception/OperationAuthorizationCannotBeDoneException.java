package com.jcooldevelopment.easybank_api.exception;

public class OperationAuthorizationCannotBeDoneException extends RuntimeException{
    public OperationAuthorizationCannotBeDoneException(String message){
        super(message);
    }
}
