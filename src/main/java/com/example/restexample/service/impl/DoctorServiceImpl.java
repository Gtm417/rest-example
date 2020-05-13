package com.example.restexample.service.impl;

import com.example.restexample.entity.Doctor;
import com.example.restexample.exception.DoctorNotFoundException;
import com.example.restexample.repository.DoctorRepository;
import com.example.restexample.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository repository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createUpdate(Doctor doctor) {
        repository.save(doctor);
    }

    @Override
    public void delete(Doctor doctor) {
        repository.delete(doctor);
    }

    @Override
    public Doctor findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor is not exist with such id: " + id));
    }

    @Override
    public List<Doctor> findAll() {
        return repository.findAll();
    }
}
