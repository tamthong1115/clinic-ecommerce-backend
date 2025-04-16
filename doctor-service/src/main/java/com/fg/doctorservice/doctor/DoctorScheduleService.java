package com.fg.doctorservice.doctor;

import com.fg.doctorservice.doctor.dto.DoctorScheduleRequest;
import com.fg.doctorservice.doctor.dto.DoctorScheduleDTO;
import java.util.List;
import java.util.UUID;

public interface DoctorScheduleService {
    DoctorScheduleDTO createSchedule(UUID doctorId, DoctorScheduleRequest request);
    DoctorScheduleDTO updateSchedule(UUID scheduleId, DoctorScheduleRequest request);
    void deleteSchedule(UUID scheduleId);
    List<DoctorScheduleDTO> getDoctorSchedules(UUID doctorId);
    List<DoctorScheduleDTO> getClinicSchedules(UUID clinicId);
    DoctorScheduleDTO getScheduleById(UUID scheduleId);
} 