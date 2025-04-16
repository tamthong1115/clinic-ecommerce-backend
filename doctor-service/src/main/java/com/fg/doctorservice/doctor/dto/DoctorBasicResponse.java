package com.fg.doctorservice.doctor.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class DoctorBasicResponse {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String gender;
    private String profilePicture;
} 