package com.jcooldevelopment.easybank_api.exception;

// Since this an expected error and is normal to appear, we use RunTimeException (Unchecked Exception). This means
// try/catch oir throws Exception (In method) are not necessary
// https://stackoverflow.com/questions/19857008/extending-exception-runtimeexception-in-java
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
