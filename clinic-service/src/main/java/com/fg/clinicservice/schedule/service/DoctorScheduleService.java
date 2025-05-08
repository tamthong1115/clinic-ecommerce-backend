package com.fg.clinicservice.schedule.service;

import com.fg.clinicservice.schedule.dto.DoctorScheduleDTO;
import com.fg.clinicservice.schedule.dto.DoctorScheduleRequest;
import com.fg.clinicservice.schedule.model.DoctorSchedule;
import com.fg.clinicservice.schedule.model.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface DoctorScheduleService {
    DoctorScheduleDTO createSchedule( UUID doctorId, DoctorScheduleRequest request);
    DoctorScheduleDTO updateSchedule(UUID clinicID, UUID scheduleId, DoctorScheduleRequest request);
    void deleteSchedule(UUID scheduleId);
    List<DoctorScheduleDTO> getDoctorSchedules();
    List<DoctorScheduleDTO> getClinicSchedules(UUID clinicId);
    DoctorScheduleDTO getScheduleById(UUID scheduleId);
    List<TimeSlot> getAvailableTimeSlots(UUID doctorId, LocalDate date);
    boolean isValidTimeSlot(UUID doctorId, LocalDate date, LocalTime startTime, LocalTime endTime);
} 