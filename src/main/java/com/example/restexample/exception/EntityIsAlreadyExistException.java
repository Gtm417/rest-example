package com.example.restexample.exception;

public class EntityIsAlreadyExistException extends RuntimeException {
    public EntityIsAlreadyExistException(String message) {
        super(message);
    }
}
