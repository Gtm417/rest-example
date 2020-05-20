package com.example.restexample.service;

import com.example.restexample.dto.PatientDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {
    void create(PatientDto patient);

    void changePatientDoctor(PatientDto patientDto);

    void deleteById(Integer id);

    PatientDto returnPatientById(Integer id);

    List<PatientDto> findAllByDoctorId(Integer id, Pageable pageable);

    List<PatientDto> findAll(Pageable pageable);
}
