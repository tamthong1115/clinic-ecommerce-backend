package com.fg.appointment.dto;

import lombok.Data;
import java.time.LocalTime;

@Data
public class TimeSlotDTO {
    private LocalTime startTime;
    private LocalTime endTime;
}