package com.fg.doctorservice.schedule.service;

import com.fg.doctorservice.schedule.dto.DoctorScheduleDTO;
import com.fg.doctorservice.schedule.dto.DoctorScheduleRequest;

import java.util.List;
import java.util.UUID;

public interface ScheduleService {
    DoctorScheduleDTO createSchedule(UUID clinicID, UUID doctorId, DoctorScheduleRequest request);
    DoctorScheduleDTO updateSchedule(UUID clinicID, UUID scheduleId, DoctorScheduleRequest request);
    void deleteSchedule(UUID scheduleId);
    List<DoctorScheduleDTO> getDoctorSchedules(UUID doctorId);
    List<DoctorScheduleDTO> getClinicSchedules(UUID clinicId);
    DoctorScheduleDTO getScheduleById(UUID scheduleId);
} 