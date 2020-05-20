package com.example.restexample.repository;

import com.example.restexample.entity.Doctor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DoctorRepository extends PagingAndSortingRepository<Doctor, Integer> {
}
