package com.jcooldevelopment.easybank_api.exception;

// Since this an expected error and is normal to appear, we use RunTimeException (Unchecked Exception). This means
// try/catch oir throws Exception (In method) are not necessary
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
