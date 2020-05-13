package com.example.restexample.service.impl;

import com.example.restexample.dto.DoctorDto;
import com.example.restexample.entity.Doctor;
import com.example.restexample.exception.DoctorNotFoundException;
import com.example.restexample.exception.EntityIsAlreadyExistException;
import com.example.restexample.repository.DoctorRepository;
import com.example.restexample.service.DoctorService;
import com.example.restexample.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository repository;
    private final Mapper<Doctor, DoctorDto> mapper;

    @Autowired
    public DoctorServiceImpl(DoctorRepository repository, Mapper<Doctor, DoctorDto> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void createUpdate(DoctorDto doctor) {
        if (!repository.findById(doctor.getId()).isPresent()) {
            repository.save(mapper.mapToEntity(doctor));
            return;
        }
        throw new EntityIsAlreadyExistException("Doctor already exist with id: " + doctor.getId());
    }

    @Override
    public void changeDoctorSpecialization(Integer doctorId, String specialization) {
        Doctor doctor = findById(doctorId);
        doctor.setSpecialization(specialization);
        repository.save(doctor);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        repository.delete(findById(id));
    }


    @Override
    public DoctorDto returnDoctorById(Integer id) {
        Doctor doctor = findById(id);
        return mapper.mapToDto(doctor);
    }

    private Doctor findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor is not exist with such id: " + id));
    }

    @Override
    public List<DoctorDto> findAll() {
        return repository.findAll()
                .stream()
                .map((mapper::mapToDto))
                .collect(Collectors.toList());
    }
}
