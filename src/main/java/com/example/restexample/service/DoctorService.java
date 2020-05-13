package com.example.restexample.service;

import com.example.restexample.entity.Doctor;

import java.util.List;

public interface DoctorService {
    void createUpdate(Doctor doctor);

    void delete(Doctor doctor);

    Doctor findById(Integer id);

    List<Doctor> findAll();
}
