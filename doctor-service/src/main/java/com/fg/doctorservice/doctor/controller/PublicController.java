package com.fg.doctorservice.doctor.controller;

import com.fg.doctorservice.doctor.service.DoctorService;
import com.fg.doctorservice.doctor.dto.DoctorBasicResponse;
import com.fg.doctorservice.doctor.dto.DoctorDetailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
    private final DoctorService doctorService;

    public PublicController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDetailResponse> getDoctorById(@PathVariable UUID id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @GetMapping
    public ResponseEntity<List<DoctorBasicResponse>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }
}
