package com.fg.appointment_service.doctor.controller;

import com.fg.appointment_service.client.user.UserDTO;
import com.fg.appointment_service.doctor.service.DoctorService;
import com.fg.appointment_service.doctor.dto.DoctorDetailResponse;
import com.fg.appointment_service.doctor.dto.DoctorRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDetailResponse> updateDoctor(@PathVariable UUID id, @Valid @RequestBody DoctorRequest doctorRequest) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorRequest));
    }

    @GetMapping("/test")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return ResponseEntity.ok(doctorService.getCurrentUser());
    }

}
