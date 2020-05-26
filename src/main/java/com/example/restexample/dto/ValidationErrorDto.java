package com.example.restexample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ValidationErrorDto {
    private String field;
    private String message;
}
