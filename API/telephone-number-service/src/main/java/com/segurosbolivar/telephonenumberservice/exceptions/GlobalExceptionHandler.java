package com.segurosbolivar.telephonenumberservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Method to capture when a customer does not exist
    @ExceptionHandler(value = NoClientExistsException.class)
    public ResponseEntity<String> noClientExistsException(NoClientExistsException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    // Method to capture when trying to assign a telephone number to a customer who already has one assigned
    @ExceptionHandler(value = AlreadyHasAssignedNumberException.class)
    public ResponseEntity<String> alreadyHasAssignedNumberException(AlreadyHasAssignedNumberException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // Method to capture when trying to assign a telephone number and there are no centers with attention in the customer's area, or they do not have availability
    @ExceptionHandler(value = NoCentersAvailableException.class)
    public ResponseEntity<String> noCentersAvailableException(NoCentersAvailableException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
