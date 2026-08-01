package com.jcooldevelopment.easybank_api.contracts.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.NoArgsConstructor;

@NoArgsConstructor // Needed by Jackson in testing
public class Apiresponse<T> {
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL) // If data is null, it won´t show it
    private T data;

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
