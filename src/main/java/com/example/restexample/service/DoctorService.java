package com.example.restexample.service;

import com.example.restexample.dto.DoctorDto;

import java.util.List;

public interface DoctorService {
    void createUpdate(DoctorDto doctor);

    void changeDoctor(Integer doctorId, DoctorDto doctorDto);

    void deleteById(Integer id);

    DoctorDto returnDoctorById(Integer id);

    List<DoctorDto> findAll();
}
