package com.example.restexample.controller;


import com.example.restexample.dto.PatientDto;
import com.example.restexample.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;


    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;

    }

    @GetMapping
    public List<PatientDto> getAllPatients(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 3) Pageable pageable) {
        return patientService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public PatientDto findPatient(@PathVariable(name = "id") Integer id) {
        return patientService.returnPatientById(id);
    }

    @PostMapping
    public ResponseEntity<Void> addPatient(@RequestBody PatientDto patient) {
        patientService.create(patient);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> changePatient(@RequestBody PatientDto patientDto) {
        patientService.changePatientDoctor(patientDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable(name = "id") Integer id) {
        patientService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
