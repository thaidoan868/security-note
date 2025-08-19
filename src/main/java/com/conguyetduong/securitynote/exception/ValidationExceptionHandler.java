package com.conguyetduong.securitynote.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

import java.util.*;

@RestControllerAdvice
public class ValidationExceptionHandler {
	
    // The exception that throws when create entities with invalid fields
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
          .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
    
    // The exception that throws when update entities with invalid fields
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(cv -> {
            // Get the last part of the property path (the field name)
            String field = cv.getPropertyPath().toString();
            if (field.contains(".")) {
                field = field.substring(field.lastIndexOf('.') + 1);
            }
            errors.put(field, cv.getMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}
