package com.fg.appointment.service;

import com.fg.appointment.dto.AppointmentDTO;
import com.fg.appointment.dto.AppointmentCreateDTO;
import com.fg.appointment.dto.TimeSlotDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentService {
    AppointmentDTO createAppointment(AppointmentCreateDTO dto);

    Optional<AppointmentDTO> getAppointmentById(UUID id);

    AppointmentDTO updateAppointment(UUID id, AppointmentCreateDTO dto);

    void deleteAppointment(UUID id);

    List<TimeSlotDTO> getBookedTimeSlots(UUID doctorId, LocalDate date);

    List<AppointmentDTO> getAppointmentsByPatientId(UUID userId);

    Map<LocalDate, List<AppointmentDTO>> getDoctorCalendarAppointments(UUID userId, LocalDate referenceDate);
}
