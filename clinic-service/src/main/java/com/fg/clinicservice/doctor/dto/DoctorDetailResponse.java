package com.fg.clinicservice.doctor.dto;

import com.fg.clinicservice.schedule.dto.DoctorScheduleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDetailResponse extends DoctorBasicResponse {

    private List<DoctorCertificationDTO> certifications;
    private List<DoctorScheduleDTO> schedules;
    private UUID clinicId;
} 