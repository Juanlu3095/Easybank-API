package com.jcooldevelopment.easybank_api.contracts.common;

import org.springframework.http.ProblemDetail;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Need this to avoid unexpected "timestamp" when doing CountryTest.createCountry_AsAdminBadFormat()
public class CustomProblemDetail extends ProblemDetail{

    private LocalDateTime timestamp;
    private Map<String, String> errors = new HashMap<>(); // HashMap with "errors"

    public void setTimestamp() {
        this.timestamp = LocalDateTime.now();
        this.setProperty("timestamp", timestamp);
    }

    public void setErrors(String errorTitle, HashMap<String, String> errorsList) {
        this.errors = errorsList;
        this.setProperty(errorTitle, errors);
    }

    public CustomProblemDetail() {
        super();
    }

}
