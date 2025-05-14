package com.fg.clinicservice.client.appointment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "appointment-service", contextId = "appointment-client-patient")
public interface AppointmentClient {
    @GetMapping("/api/v1/public/{doctorId}/date/{date}/booked-slots")
    List<TimeSlotDTO> getBookedTimeSlots(
            @PathVariable("doctorId") UUID doctorId,
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    );
}