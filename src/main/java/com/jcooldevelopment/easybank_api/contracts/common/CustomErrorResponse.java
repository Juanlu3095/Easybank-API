package com.jcooldevelopment.easybank_api.contracts.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

// This class shows sensible information. It is better to return a ProblemDetail object inside ResponseEntity directly.
public class CustomErrorResponse implements ErrorResponse{
    private HttpStatus statusCode;
    private ProblemDetail bodyDetail;

    @Override
    public HttpStatusCode getStatusCode() {
        return this.statusCode;
    }
    @Override
    public ProblemDetail getBody() {
        return this.bodyDetail;
    }

    public CustomErrorResponse(HttpStatus httpStatus, ProblemDetail detail) {
        this.statusCode = httpStatus;
        this.bodyDetail = detail;
    }

}
