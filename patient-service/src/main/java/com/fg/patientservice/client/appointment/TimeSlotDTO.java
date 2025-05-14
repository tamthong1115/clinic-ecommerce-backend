package com.fg.patientservice.client.appointment;

import lombok.Data;
import java.time.LocalTime;

@Data
public class TimeSlotDTO {
    private LocalTime startTime;
    private LocalTime endTime;
}