package com.segurosbolivar.telephonenumberservice.exceptions;

public class NoCentersAvailableException extends RuntimeException {
    public NoCentersAvailableException(String message) {
        super(message);
    }
}
