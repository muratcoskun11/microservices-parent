package com.solmaz.exception;

public class PinIsNotCorrectException extends IllegalAccessError {
    public PinIsNotCorrectException(String message) {
        super(message);
    }
}
