package com.fg.clinicservice.controller;

import com.fg.clinicservice.client.user.UserDTO;
import com.fg.clinicservice.doctor.dto.DoctorDTO;
import com.fg.clinicservice.doctor.dto.DoctorDetailResponse;
import com.fg.clinicservice.doctor.dto.DoctorIdResponse;
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
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable UUID id, @Valid @RequestBody DoctorRequest doctorRequest) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorRequest));
    }

    @GetMapping("/get-schedules")
    public ResponseEntity<List<DoctorScheduleDTO>> getDoctorSchedules() {
        return ResponseEntity.ok(scheduleService.getDoctorSchedules());
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<DoctorIdResponse> getDoctorIdByUserId(@PathVariable UUID userId) {
        UUID doctorId = doctorService.getDoctorIdByUserId(userId);
        return ResponseEntity.ok(new DoctorIdResponse(doctorId));
    }

    @PostMapping("/create-schedule")
    public ResponseEntity<DoctorScheduleDTO> createSchedule(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID doctorId,
            @Valid @RequestBody DoctorScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.createScheduleByUserId(userId,doctorId, request));
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

    @GetMapping("/{doctorId}/schedules/{scheduleId}")
    public ResponseEntity<DoctorScheduleDTO> getScheduleById(@PathVariable UUID scheduleId) {
        return ResponseEntity.ok(scheduleService.getScheduleById(scheduleId));
    }
}
