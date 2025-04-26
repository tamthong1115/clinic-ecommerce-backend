package com.fg.doctorservice.schedule;

import lombok.Data;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class DoctorScheduleDTO {
    private UUID id;
    private UUID clinicId;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
} 