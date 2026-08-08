package com.jcooldevelopment.easybank_api.exception;

public class NotEnoughBalanceException extends RuntimeException{

    public NotEnoughBalanceException(String message){
        super(message);
    }
}
