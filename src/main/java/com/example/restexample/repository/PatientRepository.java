package com.example.restexample.repository;

import com.example.restexample.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PatientRepository extends PagingAndSortingRepository<Patient, Integer> {
    Page<Patient> findAllByDoctor_Id(int doctorId, Pageable pageable);
}
