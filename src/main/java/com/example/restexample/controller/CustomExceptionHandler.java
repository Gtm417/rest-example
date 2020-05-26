package com.example.restexample.controller;

import com.example.restexample.dto.ValidationErrorDto;
import com.example.restexample.exception.DoctorNotFoundException;
import com.example.restexample.exception.EntityIsAlreadyExistException;
import com.example.restexample.exception.PatientNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        //List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        //ResponseEntity<Object> result = exampleWithMap(ex.getBindingResult(), headers, status);
        //ResponseEntity<Object> result = exampleListErrors(ex.getBindingResult(), headers, status);
        //ResponseEntity<Object> result = exampleMapWithKeyErrors(ex.getBindingResult(), headers, status);
        ResponseEntity<Object> result = exampleWithDto(ex.getBindingResult(), headers, status);
        return result;


    }

    private ResponseEntity<Object> exampleWithMap(BindingResult bindingResult,
                                                  HttpHeaders headers,
                                                  HttpStatus status) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> list = bindingResult.getFieldErrors();
        System.out.println("error " + list.get(0).getClass());
        list.forEach((x) -> map.put(x.getField(), x.getDefaultMessage()));
        return new ResponseEntity<>(map, headers, status);
    }

    private ResponseEntity<Object> exampleListErrors(BindingResult bindingResult,
                                                     HttpHeaders headers,
                                                     HttpStatus status) {

        return new ResponseEntity<>(bindingResult.getFieldErrors(), headers, status);
    }

    private ResponseEntity<Object> exampleMapWithKeyErrors(BindingResult bindingResult,
                                                           HttpHeaders headers,
                                                           HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> errors = bindingResult
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    private ResponseEntity<Object> exampleWithDto(BindingResult bindingResult,
                                                  HttpHeaders headers,
                                                  HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();

        String validationObjectName = bindingResult.getFieldErrors().get(0).getObjectName();

        List<FieldError> list = bindingResult.getFieldErrors();
        body.put("object", bindingResult.getModel().get(validationObjectName));
        body.put("errors", list.stream()
                .map((x) -> new ValidationErrorDto(x.getField(), x.getDefaultMessage()))
                .collect(Collectors.toList()));

        return new ResponseEntity<>(body, headers, status);
    }
}
