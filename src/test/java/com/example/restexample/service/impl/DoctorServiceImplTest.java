package com.example.restexample.service.impl;

import com.example.restexample.dto.DoctorDto;
import com.example.restexample.entity.Doctor;
import com.example.restexample.exception.EntityIsAlreadyExistException;
import com.example.restexample.repository.DoctorRepository;
import com.example.restexample.service.DoctorService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DoctorServiceImpl.class)
class DoctorServiceImplTest {
    private static final DoctorDto DTO = DoctorDto.builder().id(1).name("test").license("license").specialization("test").build();
    private static final Doctor ENTITY = Doctor.builder().id(1).name("test").license("license").specialization("test").build();
    private static final Pageable PAGEABLE = Pageable.unpaged();
    @Autowired
    DoctorService doctorService;

    @MockBean
    private DoctorRepository repository;

    @MockBean
    private Mapper<Doctor, DoctorDto> mapper;


    @BeforeEach
    public void setUp() {
        when(mapper.mapToEntity(DTO)).thenReturn(ENTITY);
        when(mapper.mapToDto(ENTITY)).thenReturn(DTO);
        when(repository.save(any(Doctor.class))).thenReturn(ENTITY);
        when(repository.findById(anyInt())).thenReturn(Optional.of(ENTITY));
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl(Collections.singletonList(ENTITY)));
    }

    @Test
    void createUpdateShouldVerify() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        doctorService.createUpdate(DTO);

        verify(repository).findById(anyInt());
        verify(mapper).mapToEntity(DTO);
        verify(repository).save(ENTITY);
    }

    @Test
    void createUpdateShouldThrowException() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(ENTITY));

        assertThrows(EntityIsAlreadyExistException.class, () -> doctorService.createUpdate(DTO));
        verify(repository).findById(anyInt());
    }

    @Test
    void changeDoctorSpecializationShouldVerify() {

        doctorService.changeDoctor(1, DTO);


        verify(repository).save(any(Doctor.class));
    }

    @Test
    void deleteByIdShouldVerify() {

        doctorService.deleteById(anyInt());

        verify(repository).delete(any(Doctor.class));
    }

    @Test
    void returnDoctorByIdShouldReturnEqualsDto() {
        DoctorDto actual = doctorService.returnDoctorById(1);

        verify(mapper).mapToDto(ENTITY);
        verify(repository).findById(1);
        assertEquals(DTO, actual);
    }

    @Test
    void findAllShouldReturnListOfDto() {
        List<DoctorDto> actual = doctorService.findAll(PAGEABLE);

        verify(repository).findAll(PAGEABLE);
        verify(mapper).mapToDto(ENTITY);

        assertEquals(Collections.singletonList(DTO), actual);
    }
}