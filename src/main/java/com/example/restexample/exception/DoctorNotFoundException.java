package com.example.restexample.exception;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(String message) {
        super(message);
    }
}
