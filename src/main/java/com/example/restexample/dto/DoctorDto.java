package com.example.restexample.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class DoctorDto {
    private int id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String license;
    @NotEmpty
    private String specialization;
}
