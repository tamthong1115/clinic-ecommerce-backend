package com.fg.appointment.client.clinic;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "clinic-service")
public interface ClinicClient {

    @GetMapping("/api/v1/doctor/by-user/{userId}")
    DoctorIdResponse getDoctorIdByUserId(@PathVariable UUID userId);

    // You can add a convenience method to directly get the ID
    default UUID fetchDoctorIdByUserId(UUID userId) {
        return getDoctorIdByUserId(userId).getDoctorId();
    }
}