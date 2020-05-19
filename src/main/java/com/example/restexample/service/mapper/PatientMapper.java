package com.example.restexample.service.mapper;

import com.example.restexample.dto.PatientDto;
import com.example.restexample.entity.Doctor;
import com.example.restexample.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper implements Mapper<Patient, PatientDto> {


    @Override
    public Patient mapToEntity(PatientDto patientDto) {
        return Patient.builder()
                .id(patientDto.getId())
                .firstName(patientDto.getFirstName())
                .secondName(patientDto.getSecondName())
                .diseaseDescription(patientDto.getDiseaseDescription())
                .doctor(Doctor.builder().id(patientDto.getDoctorId()).build())
                .build();
    }

    @Override
    public PatientDto mapToDto(Patient patient) {
        return PatientDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .secondName(patient.getSecondName())
                .diseaseDescription(patient.getDiseaseDescription())
                .doctorId(patient.getDoctor().getId())
                .build();
    }
}
