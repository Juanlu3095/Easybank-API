package com.jcooldevelopment.easybank_api.contracts.common;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Apiresponse<T> {
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL) // If data is null, it won´t show it
    private final T data;

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Apiresponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
