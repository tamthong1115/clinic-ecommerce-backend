package com.fg.appointment.controller;

import com.fg.appointment.dto.TimeSlotDTO;
import com.fg.appointment.service.AppointmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {

    private final AppointmentService appointmentService;

    public PublicController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/{doctorId}/date/{date}/booked-slots")
    public List<TimeSlotDTO> getBookedTimeSlots(
            @PathVariable UUID doctorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return appointmentService.getBookedTimeSlots(doctorId, date);
    }
}
