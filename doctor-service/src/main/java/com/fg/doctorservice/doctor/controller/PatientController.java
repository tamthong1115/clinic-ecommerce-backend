package com.fg.appointment_service.doctor.controller;

import com.fg.appointment_service.doctor.service.DoctorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {
    private final DoctorService doctorService;

    public PatientController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
}
