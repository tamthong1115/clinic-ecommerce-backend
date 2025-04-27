package com.fg.doctorservice.doctor.dto;

import com.fg.doctorservice.schedule.DoctorScheduleDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class DoctorResponse extends DoctorDTO {
    private List<DoctorCertificationDTO> certifications;
    private List<DoctorScheduleDTO> schedules;
    private List<UUID> clinicIds;
} 