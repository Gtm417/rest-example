package com.example.restexample.service;

import com.example.restexample.dto.PatientDto;

import java.util.List;

public interface PatientService {
    void create(PatientDto patient);

    void changePatientDoctor(PatientDto patientDto);

    void deleteById(Integer id);

    PatientDto returnPatientById(Integer id);

    List<PatientDto> findAllByDoctorId(Integer id);

    List<PatientDto> findAll();
}
