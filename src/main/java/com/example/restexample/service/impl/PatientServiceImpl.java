package com.example.restexample.service.impl;

import com.example.restexample.entity.Patient;
import com.example.restexample.exception.PatientNotFoundException;
import com.example.restexample.repository.PatientRepository;
import com.example.restexample.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

    @Autowired
    public PatientServiceImpl(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createUpdate(Patient patient) {
        repository.save(patient);
    }

    @Override
    public void delete(Patient patient) {
        repository.delete(patient);
    }

    @Override
    public Patient findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient is not exist with such id: " + id));
    }

    @Override
    public List<Patient> findAll() {
        return repository.findAll();
    }
}
