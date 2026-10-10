package com.jcooldevelopment.easybank_api.contracts.common;

import org.springframework.http.ProblemDetail;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Need this to avoid unexpected "timestamp" when doing CountryTest.createCountry_AsAdminBadFormat()
public class CustomProblemDetail extends ProblemDetail{

    private LocalDateTime timestamp;
    private Map<String, String> errors = new HashMap<>(); // HashMap with "errors"

    public void setTimestamp(LocalDateTime date) {
        this.timestamp = date;
    }

    public void setErrors(String errorTitle, HashMap<String, String> errorsList) {
        this.errors = errorsList;
    }

    // Getters are the one which allows to show properties in JSON objects
    public LocalDateTime getTimestamp(){
        return this.timestamp;
    }

    public Map<String, String> getErrors(){
        return this.errors;
    }

    public CustomProblemDetail() {
        super();
    }

}
