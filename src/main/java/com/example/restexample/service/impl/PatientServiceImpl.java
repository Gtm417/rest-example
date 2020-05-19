package com.example.restexample.service.impl;

import com.example.restexample.dto.PatientDto;
import com.example.restexample.entity.Patient;
import com.example.restexample.exception.DoctorNotFoundException;
import com.example.restexample.exception.PatientNotFoundException;
import com.example.restexample.repository.DoctorRepository;
import com.example.restexample.repository.PatientRepository;
import com.example.restexample.service.PatientService;
import com.example.restexample.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final Mapper<Patient, PatientDto> mapper;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, DoctorRepository doctorRepository, Mapper<Patient, PatientDto> mapper) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public void create(PatientDto patient) {
        if (!doctorRepository.findById(patient.getDoctorId()).isPresent()) {
            patientRepository.save(mapper.mapToEntity(patient));
            return;
        }
        throw new DoctorNotFoundException("Doctor not found with id: " + patient.getDoctorId());
    }

    @Transactional
    @Override
    public void changePatientDoctor(PatientDto patientDto) {
        doctorRepository.findById(patientDto.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id: " + patientDto.getDoctorId()));
        patientRepository.save(mapper.mapToEntity(patientDto));
    }


    @Override
    public void deleteById(Integer id) {
        patientRepository.delete(findById(id));
    }


    private Patient findById(Integer id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient is not exist with such id: " + id));
    }

    @Override
    public PatientDto returnPatientById(Integer id) {
        return mapper.mapToDto(findById(id));
    }

    @Override
    public List<PatientDto> findAllByDoctorId(Integer id) {
        return mapListToDto(patientRepository.findAllByDoctor_Id(id));
    }

    @Override
    public List<PatientDto> findAll() {
        return mapListToDto(patientRepository.findAll());
    }

    private List<PatientDto> mapListToDto(List<Patient> patients) {
        return patients.stream()
                .map((mapper::mapToDto))
                .collect(Collectors.toList());
    }
}
