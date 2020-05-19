package com.example.restexample.service.mapper;

import com.example.restexample.dto.DoctorDto;
import com.example.restexample.entity.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper implements Mapper<Doctor, DoctorDto> {

    @Override
    public Doctor mapToEntity(DoctorDto doctorDto) {
        return Doctor.builder()
                .id(doctorDto.getId())
                .name(doctorDto.getName())
                .license(doctorDto.getLicense())
                .specialization(doctorDto.getSpecialization())
                .build();
    }

    @Override
    public DoctorDto mapToDto(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .license(doctor.getLicense())
                .specialization(doctor.getSpecialization())
                .build();
    }
}
