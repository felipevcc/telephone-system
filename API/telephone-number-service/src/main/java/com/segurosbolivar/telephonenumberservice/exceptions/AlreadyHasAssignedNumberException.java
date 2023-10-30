package com.segurosbolivar.telephonenumberservice.exceptions;

public class AlreadyHasAssignedNumberException extends RuntimeException {
    public AlreadyHasAssignedNumberException(String message) {
        super(message);
    }
}
