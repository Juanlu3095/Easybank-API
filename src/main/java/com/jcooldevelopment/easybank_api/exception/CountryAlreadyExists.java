package com.jcooldevelopment.easybank_api.exception;

public class CountryAlreadyExists extends RuntimeException{
    public CountryAlreadyExists(String message){
        super(message);
    }
}
