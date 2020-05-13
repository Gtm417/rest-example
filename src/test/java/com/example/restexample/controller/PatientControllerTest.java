package com.example.restexample.controller;

import com.example.restexample.dto.PatientDto;
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
@ContextConfiguration(classes = PatientController.class)
class PatientControllerTest {
    private static final PatientDto DTO = PatientDto.builder().id(1).firstName("test").secondName("test2").diseaseDescription("testDescription").doctorId(1).build();

    @Autowired
    PatientController controller;

    @MockBean
    private PatientService service;

    @Test
    void getAllPatientsShouldReturnListDto() {
        when(service.findAll()).thenReturn(Collections.singletonList(DTO));

        List<PatientDto> actual = controller.getAllPatients();

        assertEquals(Collections.singletonList(DTO), actual);
        verify(service).findAll();
    }

    @Test
    void testGetAllPatientsByDoctorIdShouldReturnListDto() {
        when(service.findAllByDoctorId(anyInt())).thenReturn(Collections.singletonList(DTO));

        List<PatientDto> actual = controller.getAllPatientsByDoctorId(1);

        assertEquals(Collections.singletonList(DTO), actual);
        verify(service).findAllByDoctorId(1);
    }

    @Test
    void findPatientShouldReturnPatientDto() {
        when(service.returnPatientById(anyInt())).thenReturn(DTO);

        PatientDto actual = controller.findPatient(1);

        assertEquals(DTO, actual);
        verify(service).returnPatientById(1);
    }

    @Test
    void addPatientShouldReturnResponseEntityWithStatusOk() {
        ResponseEntity<Void> actual = controller.addPatient(DTO);

        verify(service).create(DTO);
        assertEquals(ResponseEntity.ok().build(), actual);

    }

    @Test
    void changePatientShouldReturnResponseEntityWithStatusOk() {
        ResponseEntity<Void> actual = controller.changePatient(DTO);

        verify(service).changePatientDoctor(DTO);
        assertEquals(ResponseEntity.ok().build(), actual);
    }

    @Test
    void deletePatientShouldReturnResponseEntityWithStatusOk() {
        ResponseEntity<Void> actual = controller.deletePatient(1);

        verify(service).deleteById(1);

        assertEquals(ResponseEntity.ok().build(), actual);
    }
}