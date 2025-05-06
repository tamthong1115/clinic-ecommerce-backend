package com.fg.clinicservice.schedule.dto;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class DoctorScheduleDTO {
    private UUID id;
    private UUID clinicId;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
} 