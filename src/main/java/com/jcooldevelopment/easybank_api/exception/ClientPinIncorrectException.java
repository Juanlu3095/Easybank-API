package com.jcooldevelopment.easybank_api.exception;

public class ClientPinIncorrectException extends RuntimeException{
    public ClientPinIncorrectException(String message){
        super(message);
    }
}
