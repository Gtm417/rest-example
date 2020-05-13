package com.example.restexample.controller;

import com.example.restexample.dto.DoctorDto;
import com.example.restexample.exception.DoctorNotFoundException;
import com.example.restexample.exception.EntityIsAlreadyExistException;
import com.example.restexample.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @GetMapping
    public List<DoctorDto> getAllDoctors() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public DoctorDto findDoctor(@PathVariable(name = "id") Integer id) {
        return service.returnDoctorById(id);
    }

    @PostMapping
    public void addDoctor(@RequestBody DoctorDto doctorDto) {
        service.createUpdate(doctorDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> changeDoctorSpecialization(@RequestBody String specialization, @PathVariable(name = "id") Integer id) {
        service.changeDoctorSpecialization(id, specialization);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable(name = "id") Integer id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(EntityIsAlreadyExistException.class)
    public ResponseEntity<Void> entityIsAlreadyExistHandling() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<Void> doctorNotFoundHandling() {
        return ResponseEntity.notFound().build();
    }
}
