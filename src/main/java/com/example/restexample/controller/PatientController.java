package com.example.restexample.controller;


import com.example.restexample.dto.PatientDto;
import com.example.restexample.exception.DoctorNotFoundException;
import com.example.restexample.exception.PatientNotFoundException;
import com.example.restexample.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;


    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;

    }


    @GetMapping
    public List<PatientDto> getAllPatients() {
        return patientService.findAll();
    }

    @GetMapping("/doctors/{id}")
    public List<PatientDto> getAllPatientsByDoctorId(@PathVariable(name = "id") Integer id) {
        return patientService.findAllByDoctorId(id);
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

    @ExceptionHandler({PatientNotFoundException.class, DoctorNotFoundException.class})
    public ResponseEntity<Void> patientNotFoundHandling() {
        return ResponseEntity.notFound().build();
    }
}
