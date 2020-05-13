package com.example.restexample.service;

import com.example.restexample.dto.DoctorDto;

import java.util.List;

public interface DoctorService {
    void createUpdate(DoctorDto doctor);

    void changeDoctorSpecialization(Integer doctorId, String specialization);

    void deleteById(Integer id);

    DoctorDto returnDoctorById(Integer id);

    List<DoctorDto> findAll();
}
