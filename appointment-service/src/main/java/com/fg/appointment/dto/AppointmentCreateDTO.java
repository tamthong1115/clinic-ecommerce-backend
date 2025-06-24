package com.fg.appointment.dto;

import com.fg.appointment.model.Appointment.AppointmentStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class AppointmentCreateDTO {
    private UUID doctorId;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorProfilePicture;
    private UUID patientId;
    private UUID clinicId;
    private String clinicName;
    private String clinicAddress;
    private UUID serviceId;
    private String serviceName;
    private String servicePrice;
    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private AppointmentStatus status;
    private String reason;
}