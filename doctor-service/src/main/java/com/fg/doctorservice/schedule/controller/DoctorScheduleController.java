package com.fg.doctorservice.schedule.controller;

import com.fg.doctorservice.schedule.dto.DoctorScheduleDTO;
import com.fg.doctorservice.schedule.dto.DoctorScheduleRequest;
import com.fg.doctorservice.schedule.service.DoctorScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clinic/{clinicID}/doctors/{doctorId}/schedules")
public class ScheduleController {

    private final DoctorScheduleService scheduleService;

    public ScheduleController(DoctorScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<DoctorScheduleDTO> createSchedule(
            @PathVariable UUID clinicID,
            @PathVariable UUID doctorId,
            @Valid @RequestBody DoctorScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.createSchedule(clinicID, doctorId, request));
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<DoctorScheduleDTO> updateSchedule(
            @PathVariable UUID clinicID,
            @PathVariable UUID scheduleId,
            @Valid @RequestBody DoctorScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.updateSchedule(clinicID, scheduleId, request));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable UUID scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DoctorScheduleDTO>> getClinicSchedules(@PathVariable UUID clinicID) {
        return ResponseEntity.ok(scheduleService.getClinicSchedules(clinicID));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<DoctorScheduleDTO> getScheduleById(@PathVariable UUID scheduleId) {
        return ResponseEntity.ok(scheduleService.getScheduleById(scheduleId));
    }
}
