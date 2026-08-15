package com.jcooldevelopment.easybank_api.exception;

public class OrdererAndBeneficiaryCannotBeSameException extends RuntimeException{
    public OrdererAndBeneficiaryCannotBeSameException(String message){
        super(message);
    }
}
