package com.jcooldevelopment.easybank_api.config;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFoundException (ResourceNotFoundException exception) {
        // ProblemDetail follows RFC for showing errors
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
            "The requested resource does not exists.");
        problemDetails.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/404"));
        problemDetails.setTitle("Resource not found");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetails);
    }

    // 422 exception with MethodArgumentNotValidException interception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException (MethodArgumentNotValidException exception) {

        // Unordered list with key (name of field with error) and value (message of error)
        HashMap<String, String> errors = new HashMap<>();
        for (FieldError fieldError: exception.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_CONTENT,
            "One or more fields have wrong format.");
        problemDetails.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/422"));
        problemDetails.setTitle("Request body not valid");
        problemDetails.setProperty("timestamp", LocalDateTime.now().toString());
        problemDetails.setProperty("errors", errors);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(problemDetails);
    }

    // 500 exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException (Exception exception) {
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE,
            "This service is not available right now. Please try later.");
        problemDetails.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/500"));
        problemDetails.setTitle("Service unavailable");

        // See for loggin in production: https://www.baeldung.com/spring-boot-logging

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(problemDetails);
    }
}
