package com.example.restexample.controller;

import com.example.restexample.dto.DoctorDto;
import com.example.restexample.dto.PatientDto;
import com.example.restexample.service.DoctorService;
import com.example.restexample.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService service;
    private final PatientService patientService;

    public DoctorController(DoctorService service, PatientService patientService) {
        this.service = service;
        this.patientService = patientService;
    }

    @GetMapping
    public List<DoctorDto> getAllDoctors() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public DoctorDto findDoctor(@PathVariable(name = "id") Integer id) {
        return service.returnDoctorById(id);
    }

    @GetMapping("/{id}/patients")
    public List<PatientDto> getAllPatientsByDoctorId(@PathVariable(name = "id") Integer id) {
        return patientService.findAllByDoctorId(id);
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
