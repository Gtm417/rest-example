package com.example.restexample.controller;

import com.example.restexample.dto.DoctorDto;
import com.example.restexample.service.DoctorService;
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
    private static final DoctorDto DTO = DoctorDto.builder().id(1).name("test").license("license").specialization("test").build();

    @Autowired
    DoctorController controller;

    @MockBean
    private DoctorService service;

    @Test
    void getAllDoctorsShouldReturnListDto() {
        when(service.findAll()).thenReturn(Collections.singletonList(DTO));

        List<DoctorDto> actual = controller.getAllDoctors();

        assertEquals(Collections.singletonList(DTO), actual);
        verify(service).findAll();
    }

    @Test
    void findDoctorShouldReturnDoctorDto() {
        when(service.returnDoctorById(anyInt())).thenReturn(DTO);

        DoctorDto actual = controller.findDoctor(1);

        assertEquals(DTO, actual);
        verify(service).returnDoctorById(1);
    }

    @Test
    void addDoctorShouldUseCreateMethod() {

        controller.addDoctor(DTO);

        verify(service).createUpdate(DTO);
    }

    @Test
    void changeDoctorSpecializationShouldReturnResponseEntityWithStatusOk() {
        ResponseEntity<Void> actual = controller.changeDoctorSpecialization("test", 1);

        verify(service).changeDoctorSpecialization(1, "test");

        assertEquals(ResponseEntity.ok().build(), actual);
    }

    @Test
    void deleteDoctor() {
        ResponseEntity<Void> actual = controller.deleteDoctor(1);

        verify(service).deleteById(1);

        assertEquals(ResponseEntity.ok().build(), actual);
    }

}