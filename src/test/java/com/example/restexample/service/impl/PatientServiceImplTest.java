package com.example.restexample.service.impl;

import com.example.restexample.dto.PatientDto;
import com.example.restexample.entity.Doctor;
import com.example.restexample.entity.Patient;
import com.example.restexample.exception.DoctorNotFoundException;
import com.example.restexample.repository.DoctorRepository;
import com.example.restexample.repository.PatientRepository;
import com.example.restexample.service.PatientService;
import com.example.restexample.service.mapper.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PatientServiceImpl.class)
class PatientServiceImplTest {
    private static final PatientDto DTO = PatientDto.builder().id(1).firstName("test").secondName("test2").diseaseDescription("testDescription").doctorId(1).build();
    private static final Patient ENTITY = Patient.builder().id(1).firstName("test").secondName("test2").diseaseDescription("testDescription").build();
    private static final Pageable PAGEABLE = Pageable.unpaged();
    @Autowired
    PatientService service;

    @MockBean
    private Mapper<Patient, PatientDto> mapper;
    @MockBean
    private PatientRepository patientRepository;
    @MockBean
    private DoctorRepository doctorRepository;

    @BeforeEach
    public void setUp() {
        when(mapper.mapToEntity(DTO)).thenReturn(ENTITY);
        when(mapper.mapToDto(ENTITY)).thenReturn(DTO);
        when(patientRepository.save(any(Patient.class))).thenReturn(ENTITY);
        when(patientRepository.findById(anyInt())).thenReturn(Optional.of(ENTITY));
        when(patientRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl(Collections.singletonList(ENTITY)));
        when(patientRepository.findAllByDoctor_Id(anyInt(), any(Pageable.class))).thenReturn(new PageImpl(Collections.singletonList(ENTITY)));
        when(doctorRepository.findById(anyInt())).thenReturn(Optional.of(new Doctor()));
    }

    @Test
    void create() {
        when(doctorRepository.findById(anyInt())).thenReturn(Optional.empty());

        service.create(DTO);

        verify(doctorRepository).findById(anyInt());
        verify(mapper).mapToEntity(DTO);
        verify(patientRepository).save(ENTITY);
    }

    @Test
    void createShouldThrowDoctorNotFoundException() {

        assertThrows(DoctorNotFoundException.class,
                () -> service.create(DTO));
        verify(doctorRepository).findById(anyInt());
        verify(mapper, times(0)).mapToEntity(DTO);
        verify(patientRepository, times(0)).save(ENTITY);
    }

    @Test
    void changePatientDoctorShouldVerifyIfDoctorIsExist() {
        service.changePatientDoctor(DTO);

        verify(doctorRepository).findById(anyInt());
        verify(patientRepository).save(ENTITY);
        verify(mapper).mapToEntity(DTO);
    }

    @Test
    void changePatientDoctorShouldThrowExceptionIfDoctorNotExist() {
        when(doctorRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class,
                () -> service.changePatientDoctor(DTO));

        verify(doctorRepository).findById(anyInt());
        verify(patientRepository, times(0)).save(any(Patient.class));

    }

    @Test
    void deleteById() {
        service.deleteById(anyInt());

        verify(patientRepository).delete(any(Patient.class));
    }

    @Test
    void returnPatientById() {

        PatientDto actual = service.returnPatientById(1);

        verify(mapper).mapToDto(ENTITY);
        verify(patientRepository).findById(1);
        assertEquals(DTO, actual);

    }

    @Test
    void findAllByDoctorId() {
        List<PatientDto> actual = service.findAllByDoctorId(1, PAGEABLE);

        verify(patientRepository).findAllByDoctor_Id(1, PAGEABLE);
        verify(mapper).mapToDto(ENTITY);

        assertEquals(Collections.singletonList(DTO), actual);
    }

    @Test
    void findAll() {
        List<PatientDto> actual = service.findAll(PAGEABLE);

        verify(patientRepository).findAll(PAGEABLE);
        verify(mapper).mapToDto(ENTITY);

        assertEquals(Collections.singletonList(DTO), actual);
    }
}