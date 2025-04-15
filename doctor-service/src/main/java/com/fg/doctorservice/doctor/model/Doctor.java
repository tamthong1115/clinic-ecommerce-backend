package com.fg.doctorservice.doctor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Doctor {
    @Id
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String gender;
    private String profile_picture;
    private Integer experience_years;
    private String education;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<DoctorCertification> doctorCertifications;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<DoctorSchedule> doctorSchedules;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<DoctorClinic> doctorClinics;
}
