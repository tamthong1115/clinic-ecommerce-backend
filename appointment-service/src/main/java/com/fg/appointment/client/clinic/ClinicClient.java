package com.fg.appointment.client.clinic;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "clinic-service")
public interface ClinicClient {

    @GetMapping("/api/v1/doctor/by-user/{userId}")
    DoctorIdResponse getDoctorIdByUserId(@PathVariable UUID userId);


    @GetMapping("/api/v1/patient/by-user/{userId}")
    PatientIdResponse getPatientIdByUserId(@PathVariable UUID userId);

}