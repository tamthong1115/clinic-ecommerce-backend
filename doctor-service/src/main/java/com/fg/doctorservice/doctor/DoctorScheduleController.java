package com.fg.doctorservice.doctor;

import com.fg.doctorservice.doctor.dto.DoctorScheduleRequest;
import com.fg.doctorservice.doctor.dto.DoctorScheduleDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/doctors/{doctorId}/schedules")
@RequiredArgsConstructor
public class DoctorScheduleController {

    private final DoctorScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<DoctorScheduleDTO> createSchedule(
            @PathVariable UUID doctorId,
            @Valid @RequestBody DoctorScheduleRequest request) {
        return new ResponseEntity<>(scheduleService.createSchedule(doctorId, request), HttpStatus.CREATED);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<DoctorScheduleDTO> updateSchedule(
            @PathVariable UUID scheduleId,
            @Valid @RequestBody DoctorScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleId, request));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable UUID scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DoctorScheduleDTO>> getDoctorSchedules(@PathVariable UUID doctorId) {
        return ResponseEntity.ok(scheduleService.getDoctorSchedules(doctorId));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<DoctorScheduleDTO> getScheduleById(@PathVariable UUID scheduleId) {
        return ResponseEntity.ok(scheduleService.getScheduleById(scheduleId));
    }

    @GetMapping("/clinics/{clinicId}")
    public ResponseEntity<List<DoctorScheduleDTO>> getClinicSchedules(@PathVariable UUID clinicId) {
        return ResponseEntity.ok(scheduleService.getClinicSchedules(clinicId));
    }
} 