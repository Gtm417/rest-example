package com.example.restexample.repository;

import com.example.restexample.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    List<Patient> findAllByDoctor_Id(Integer id);
}
