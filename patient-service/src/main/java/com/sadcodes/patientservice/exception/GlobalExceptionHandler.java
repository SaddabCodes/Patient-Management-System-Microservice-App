package com.sadcodes.patientservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // This annotation tells Spring:
    // "If MethodArgumentNotValidException occurs, handle it using this method"
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {

        // Create a map to store field name -> error message
        // Example: { "email": "Email is required", "name": "Name cannot be blank" }
        Map<String, String> error = new HashMap<>();

        // Get all validation errors from the exception
        // These errors come from @Valid + validation annotations like @NotBlank, @Email, etc.
        ex.getBindingResult().getFieldErrors().forEach(err -> {

            // err.getField() → gives the field name (e.g., "email")
            // err.getDefaultMessage() → gives the validation message (e.g., "Email is required")

            // Put field and its error message into the map
            error.put(err.getField(), err.getDefaultMessage());
        });

        // Return HTTP 400 (Bad Request) with the error map as response body
        return ResponseEntity.badRequest().body(error);
    }


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        log.warn("Email address already exists {}",ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message","Email address already exists");
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String ,String >>handlePatientNotFoundException(PatientNotFoundException ex){
        log.warn("Patient not found {}",ex.getMessage());


        Map<String ,String > errors = new HashMap<>();
        errors.put("message","Patient not found");
        return ResponseEntity.badRequest().body(errors);
    }
}
