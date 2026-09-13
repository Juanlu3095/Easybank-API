package com.jcooldevelopment.easybank_api.exception;

public class ClientPinAlreadySetException extends RuntimeException{
    public ClientPinAlreadySetException(String message){
        super(message);
    }
}
