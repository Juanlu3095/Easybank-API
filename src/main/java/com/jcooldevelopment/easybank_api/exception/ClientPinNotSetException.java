package com.jcooldevelopment.easybank_api.exception;

public class ClientPinNotSetException extends RuntimeException{
    public ClientPinNotSetException(String message){
        super(message);
    }
}
