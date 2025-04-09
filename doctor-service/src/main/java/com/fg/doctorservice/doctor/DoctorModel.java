package com.fg.doctorservice.doctor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class DoctorModel {
    @Id
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String gender;
    private String profile_picture;
    private Integer experience_years;
    private String education;

}
