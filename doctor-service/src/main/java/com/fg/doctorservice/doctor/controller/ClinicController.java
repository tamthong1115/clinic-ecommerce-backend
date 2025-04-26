package com.fg.doctorservice.doctor.controller;

import com.fg.doctorservice.doctor.service.DoctorService;
import com.fg.doctorservice.doctor.dto.DoctorDetailResponse;
import com.fg.doctorservice.doctor.dto.DoctorRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clinic")
public class ClinicController {
    private final DoctorService doctorService;

    public ClinicController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/{clinic_id}")
    public ResponseEntity<DoctorDetailResponse> createDoctorWithClinicId(
            @PathVariable UUID clinic_id,
            @Valid @RequestBody DoctorRequest doctorRequest) {
        return new ResponseEntity<>(doctorService.createDoctor(clinic_id, doctorRequest), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<DoctorDetailResponse> createDoctorWithoutClinicId(
            @Valid @RequestBody DoctorRequest doctorRequest) {
        return new ResponseEntity<>(doctorService.createDoctor(null, doctorRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable UUID id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
