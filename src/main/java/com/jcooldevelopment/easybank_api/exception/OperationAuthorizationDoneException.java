package com.jcooldevelopment.easybank_api.exception;

public class OperationAuthorizationDoneException extends RuntimeException{
    public OperationAuthorizationDoneException(String message){
        super(message);
    }
}
