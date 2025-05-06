package com.fg.clinicservice.controller;

import com.fg.clinicservice.client.user.UserDTO;
import com.fg.clinicservice.doctor.dto.DoctorDetailResponse;
import com.fg.clinicservice.doctor.dto.DoctorRequest;
import com.fg.clinicservice.doctor.service.DoctorService;
import com.fg.clinicservice.schedule.dto.DoctorScheduleDTO;
import com.fg.clinicservice.schedule.dto.DoctorScheduleRequest;
import com.fg.clinicservice.schedule.service.DoctorScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {
    private final DoctorService doctorService;
    private final DoctorScheduleService scheduleService;

    public DoctorController(DoctorService doctorService, DoctorScheduleService scheduleService) {
        this.doctorService = doctorService;
        this.scheduleService = scheduleService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDetailResponse> updateDoctor(@PathVariable UUID id, @Valid @RequestBody DoctorRequest doctorRequest) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorRequest));
    }

    @GetMapping("/test")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return ResponseEntity.ok(doctorService.getCurrentUser());
    }

    @PostMapping("/{doctorId}/schedules")
    public ResponseEntity<DoctorScheduleDTO> createSchedule(
            @PathVariable UUID doctorId,
            @Valid @RequestBody DoctorScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.createSchedule( doctorId, request));
    }

    @PutMapping("/{doctorId}/schedules/{scheduleId}")
    public ResponseEntity<DoctorScheduleDTO> updateSchedule(
            @PathVariable UUID clinicID,
            @PathVariable UUID scheduleId,
            @Valid @RequestBody DoctorScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.updateSchedule(clinicID, scheduleId, request));
    }

    @DeleteMapping("/{doctorId}/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable UUID scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{doctorId}/schedules")
    public ResponseEntity<List<DoctorScheduleDTO>> getClinicSchedules(@PathVariable UUID clinicID) {
        return ResponseEntity.ok(scheduleService.getClinicSchedules(clinicID));
    }

    @GetMapping("/{doctorId}/schedules/{scheduleId}")
    public ResponseEntity<DoctorScheduleDTO> getScheduleById(@PathVariable UUID scheduleId) {
        return ResponseEntity.ok(scheduleService.getScheduleById(scheduleId));
    }
}
