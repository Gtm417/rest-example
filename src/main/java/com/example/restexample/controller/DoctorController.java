package com.example.restexample.controller;

import com.example.restexample.dto.DoctorDto;
import com.example.restexample.dto.PatientDto;
import com.example.restexample.service.DoctorService;
import com.example.restexample.service.PatientService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService service;
    private final PatientService patientService;

    public DoctorController(DoctorService service, PatientService patientService) {
        this.service = service;
        this.patientService = patientService;
    }


    @GetMapping
    public List<DoctorDto> getAllDoctors(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 4) Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public DoctorDto findDoctor(@PathVariable(name = "id") Integer id) {
        return service.returnDoctorById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/patients")
    public List<PatientDto> getAllPatientsByDoctorId(@PathVariable(name = "id") Integer id,
                                                     @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 4) Pageable pageable) {
        return patientService.findAllByDoctorId(id, pageable);
    }

    @PostMapping
    public void addDoctor(@RequestBody DoctorDto doctorDto) {
        service.createUpdate(doctorDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> changeDoctorInfo(@RequestBody DoctorDto doctorDto, @PathVariable(name = "id") Integer id) {
        service.changeDoctor(id, doctorDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable(name = "id") Integer id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
