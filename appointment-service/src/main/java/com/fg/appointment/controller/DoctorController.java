package com.fg.appointment.controller;

import com.fg.appointment.dto.AppointmentCreateDTO;
import com.fg.appointment.dto.AppointmentDTO;
import com.fg.appointment.dto.TimeSlotDTO;
import com.fg.appointment.service.AppointmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {
    private final AppointmentService appointmentService;

    public DoctorController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable UUID id, @RequestBody AppointmentCreateDTO dto) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable UUID id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/calendar-appointments")
    public ResponseEntity<Map<LocalDate, List<AppointmentDTO>>> getCalendarAppointments(
            @RequestParam UUID userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate referenceDate) {

        // If referenceDate is not provided, use today
        if (referenceDate == null) {
            referenceDate = LocalDate.now();
        }

        Map<LocalDate, List<AppointmentDTO>> calendarData =
                appointmentService.getDoctorCalendarAppointments(userId, referenceDate);

        return ResponseEntity.ok(calendarData);
    }


}
