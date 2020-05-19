package com.example.restexample.controller;

import com.example.restexample.dto.DoctorDto;
import com.example.restexample.dto.PatientDto;
import com.example.restexample.service.DoctorService;
import com.example.restexample.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DoctorController.class)
class DoctorControllerTest {
    private static final PatientDto PATIENT_DTO = PatientDto.builder().id(1).firstName("test").secondName("test2").diseaseDescription("testDescription").doctorId(1).build();
    private static final DoctorDto DOCTOR_DTO = DoctorDto.builder().id(1).name("test").license("license").specialization("test").build();

    @Autowired
    DoctorController controller;

    @MockBean
    private DoctorService service;

    @MockBean
    private PatientService patientService;

    @Test
    void getAllDoctorsShouldReturnListDto() {
        when(service.findAll()).thenReturn(Collections.singletonList(DOCTOR_DTO));

        List<DoctorDto> actual = controller.getAllDoctors();

        assertEquals(Collections.singletonList(DOCTOR_DTO), actual);
        verify(service).findAll();
    }

    @Test
    void testGetAllPatientsByDoctorIdShouldReturnListDto() {
        when(patientService.findAllByDoctorId(anyInt())).thenReturn(Collections.singletonList(PATIENT_DTO));

        List<PatientDto> actual = controller.getAllPatientsByDoctorId(1);

        assertEquals(Collections.singletonList(PATIENT_DTO), actual);
        verify(patientService).findAllByDoctorId(1);
    }

    @Test
    void findDoctorShouldReturnDoctorDto() {
        when(service.returnDoctorById(anyInt())).thenReturn(DOCTOR_DTO);

        DoctorDto actual = controller.findDoctor(1);

        assertEquals(DOCTOR_DTO, actual);
        verify(service).returnDoctorById(1);
    }

    @Test
    void addDoctorShouldUseCreateMethod() {

        controller.addDoctor(DOCTOR_DTO);

        verify(service).createUpdate(DOCTOR_DTO);
    }

    @Test
    void changeDoctorSpecializationShouldReturnResponseEntityWithStatusOk() {
        ResponseEntity<Void> actual = controller.changeDoctorInfo(DOCTOR_DTO, 1);

        verify(service).changeDoctor(1, DOCTOR_DTO);

        assertEquals(ResponseEntity.ok().build(), actual);
    }

    @Test
    void deleteDoctor() {
        ResponseEntity<Void> actual = controller.deleteDoctor(1);

        verify(service).deleteById(1);

        assertEquals(ResponseEntity.ok().build(), actual);
    }

}