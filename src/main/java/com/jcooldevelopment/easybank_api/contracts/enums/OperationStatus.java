package com.jcooldevelopment.easybank_api.contracts.enums;

public enum OperationStatus {
    PENDING, // The outsider bank needs to verify
    PENDING_AUTHORIZATION, // Needs operation authorization
    DONE, // Operation completed
    CANCELED,
    BLOCKED
}
