package com.fg.doctorservice.doctor.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class DoctorDTO {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String gender;
    private String profilePicture;
    private Integer experienceYears;
    private String education;
} 