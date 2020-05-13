package com.example.restexample.service;

import com.example.restexample.entity.Patient;

import java.util.List;

public interface PatientService {
    void createUpdate(Patient patient);

    void delete(Patient patient);

    Patient findById(Integer id);

    List<Patient> findAll();
}
