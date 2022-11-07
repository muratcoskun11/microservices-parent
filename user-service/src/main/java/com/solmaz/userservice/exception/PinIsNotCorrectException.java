package com.solmaz.userservice.exception;

public class PinIsNotCorrectException extends IllegalAccessError {
    public PinIsNotCorrectException(String message) {
        super(message);
    }
}
