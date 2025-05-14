package com.fg.appointment.dto;

import com.fg.appointment.model.Appointment.AppointmentStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class AppointmentCreateDTO {
    private UUID doctorId;
    private UUID patientId;
    private UUID clinicId;
    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private AppointmentStatus status;
}