package com.example.restexample.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientDto {
    private int id;
    private String firstName;
    private String secondName;
    private String diseaseDescription;
    private int doctorId;
}
