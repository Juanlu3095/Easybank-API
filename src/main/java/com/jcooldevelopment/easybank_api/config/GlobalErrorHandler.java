package com.jcooldevelopment.easybank_api.config;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.security.core.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jcooldevelopment.easybank_api.exception.ActivationCodeExpiredException;
import com.jcooldevelopment.easybank_api.exception.DniAlreadyExistsException;
import com.jcooldevelopment.easybank_api.exception.EmailAlreadyExistsException;
import com.jcooldevelopment.easybank_api.exception.EmailCouldNotBeSendException;
import com.jcooldevelopment.easybank_api.exception.EncryptionException;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.exception.UserAlreadyEnabledException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalErrorHandler {

    // 401 Exception
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ProblemDetail> handleAuthenticationException (AuthenticationException exception) {
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,
            exception.getMessage());
        problemDetails.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/401"));
        problemDetails.setTitle("Credentials not valid.");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetails);
    }

    // JWT Exceptions for 401 errors
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ProblemDetail> handleSignatureException (SignatureException exception) {
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,
            "Credentials not valid.");
        problemDetails.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/401"));
        problemDetails.setTitle("Credentials not valid.");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetails);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ProblemDetail> handleExpiredJwtException (ExpiredJwtException exception) {
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,
            "Credentials expired.");
        problemDetails.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/401"));
        problemDetails.setTitle("Credentials expired.");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetails);
    }

    // 404 exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFoundException (ResourceNotFoundException exception) {
        // ProblemDetail follows RFC for showing errors
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
            exception.getMessage());
        problemDetails.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/404"));
        problemDetails.setTitle("Resource not found");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetails);
    }

    // 409 exception for email
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleEmailAlreadyExistsException (EmailAlreadyExistsException exception) {
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
            exception.getMessage());
        problemDetails.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/409"));
        problemDetails.setTitle("Email already exists");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetails);
    }

    // 409 exception for DNI
    @ExceptionHandler(DniAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleDniAlreadyExistsException (DniAlreadyExistsException exception) {
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
            exception.getMessage());
        problemDetails.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/409"));
        problemDetails.setTitle("DNI already exists");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetails);
    }

    // Exception for an expired activation code. Code error 410
    @ExceptionHandler(ActivationCodeExpiredException.class)
    public ResponseEntity<ProblemDetail> handleActivationCodeExpiredException (ActivationCodeExpiredException exception) {
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.GONE,
            exception.getMessage());
        problemDetails.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/410"));
        problemDetails.setTitle("Activation code expired");

        return ResponseEntity.status(HttpStatus.GONE).body(problemDetails);
    }

    // Exception for an already enabled user. Code error 410
    @ExceptionHandler(UserAlreadyEnabledException.class)
    public ResponseEntity<ProblemDetail> handleUserAlreadyEnabled (UserAlreadyEnabledException exception) {
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.GONE,
            exception.getMessage());
        problemDetails.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/410"));
        problemDetails.setTitle("Account already enabled");

        return ResponseEntity.status(HttpStatus.GONE).body(problemDetails);
    }

    // 422 exception with MethodArgumentNotValidException interception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException (MethodArgumentNotValidException exception) {

        // Unordered list with key (name of field with error) and value (message of error)
        // https://stackoverflow.com/questions/79301881/spring-problem-detail-response-how-to-add-custom-fields
        HashMap<String, String> errors = new HashMap<>();
        for (FieldError fieldError: exception.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        // https://howtodoinjava.com/spring-mvc/spring-problemdetail-errorresponse/
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_CONTENT,
            "One or more fields have wrong format.");
        problemDetails.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/422"));
        problemDetails.setTitle("Request body not valid");
        problemDetails.setProperty("timestamp", LocalDateTime.now().toString());
        problemDetails.setProperty("errors", errors);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(problemDetails);
    }

    // Exception for RequestParams in controllers, which is a 422 error
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolationException (ConstraintViolationException exception) {
        HashSet<String> errors = new HashSet<>();
        for (ConstraintViolation<?> error: exception.getConstraintViolations()) {
            errors.add(error.getMessage());
        }
        
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_CONTENT,
            "One or more params are wrong.");
        problemDetails.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/422"));
        problemDetails.setTitle("Request params not valid");
        problemDetails.setProperty("timestamp", LocalDateTime.now().toString());
        problemDetails.setProperty("errors", errors);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(problemDetails);
    }

    // ERROR HANDLER WHEN A FIELD DATA IS REPEATED AND MUST BE UNIQUE

    // 500 exception
    /* @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException (Exception exception) {
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
            "This service is not available right now. Please try later.");
        problemDetails.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/500"));
        problemDetails.setTitle("Service unavailable");

        // See for logs in production: https://www.baeldung.com/spring-boot-logging

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetails);
    } */

    // Message exception for crypto errors
    @ExceptionHandler(EncryptionException.class)
    public ResponseEntity<ProblemDetail> handleEncryptionException (EncryptionException exception) {
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
            exception.getMessage());
        problemDetails.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/500"));
        problemDetails.setTitle("Service unavailable.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetails);
    }

    // Message Exception from jakarta.mail
    @ExceptionHandler(EmailCouldNotBeSendException.class)
    public ResponseEntity<ProblemDetail> handleMessagingException (EmailCouldNotBeSendException exception) {
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY,
            exception.getMessage());
        problemDetails.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/502"));
        problemDetails.setTitle("Message could not be send.");

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(problemDetails);
    }
}
