package com.jcooldevelopment.easybank_api.contracts.enums;

public enum OperationType {
    PAYMENT, // For taxes and payment receipt
    TRANSFER, // Move money between accounts of the same user
    MONEY_TRANSFER, // Move money to another person account, even outside of our bank
    BALANCE_ADJUSTMENT // For bugs in system, making accounts balance incorrect
}
