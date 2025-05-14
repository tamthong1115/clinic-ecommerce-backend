package com.fg.clinicservice.doctor.dto;

import com.fg.clinicservice.doctor.model.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorBasicResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private String profilePicture;
    private Integer experienceYears;
    private String education;
    private Doctor.DoctorStatus status;
} 