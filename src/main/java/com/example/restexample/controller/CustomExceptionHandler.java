package com.example.restexample.controller;

import com.example.restexample.exception.DoctorNotFoundException;
import com.example.restexample.exception.EntityIsAlreadyExistException;
import com.example.restexample.exception.PatientNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PatientNotFoundException.class, DoctorNotFoundException.class})
    public ResponseEntity<Void> patientNotFoundHandling() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(EntityIsAlreadyExistException.class)
    public ResponseEntity<Void> entityIsAlreadyExistHandling() {
        return ResponseEntity.badRequest().build();
    }

}
