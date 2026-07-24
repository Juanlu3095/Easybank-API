package com.jcooldevelopment.easybank_api.exception;

public class ResourceAlreadyExists extends RuntimeException{
    public ResourceAlreadyExists(String message){
        super(message);
    }
}
