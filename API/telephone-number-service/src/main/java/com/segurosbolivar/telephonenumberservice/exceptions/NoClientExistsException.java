package com.segurosbolivar.telephonenumberservice.exceptions;

public class NoClientExistsException extends RuntimeException {
    public NoClientExistsException(String message) {
        super(message);
    }
}
