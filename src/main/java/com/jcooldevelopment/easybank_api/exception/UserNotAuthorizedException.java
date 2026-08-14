package com.jcooldevelopment.easybank_api.exception;

public class UserNotAuthorizedException extends RuntimeException{
    public UserNotAuthorizedException(String message){
        super(message);
    }
}
