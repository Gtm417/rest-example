package com.example.restexample.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorDto {
    private int id;
    private String name;
    private String license;
    private String specialization;
}
