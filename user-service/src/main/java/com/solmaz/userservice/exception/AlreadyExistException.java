package com.solmaz.userservice.exception;

import lombok.Getter;

@Getter
public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String message) {
        super(message);
    }
}