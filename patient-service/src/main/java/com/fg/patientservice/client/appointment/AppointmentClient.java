package com.fg.patientservice.client.appointment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "appointment-service", contextId = "appointment-client-patient")
public interface AppointmentClient {
    @GetMapping("/appointments/api/v1/doctor/{doctorId}/date/{date}/booked-slots")
    List<TimeSlotDTO> getBookedTimeSlots(@PathVariable("doctorId") UUID doctorId, @PathVariable("date") LocalDate date);
}