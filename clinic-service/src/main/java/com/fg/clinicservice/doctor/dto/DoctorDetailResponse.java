package com.fg.clinicservice.doctor.dto;

import com.fg.clinicservice.schedule.dto.DoctorScheduleDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class DoctorDetailResponse extends DoctorBasicResponse {
    private Integer experienceYears;
    private String education;
    private List<DoctorCertificationDTO> certifications;
    private List<DoctorScheduleDTO> schedules;
    private UUID clinicIds;
} 