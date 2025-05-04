package com.fg.doctorservice.doctor.model;

import com.fg.doctorservice.schedule.DoctorSchedule;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String phone;
    private String gender;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "license_number")
    private String licenseNumber;

    private String education;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<DoctorCertification> doctorCertifications;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<DoctorSchedule> doctorSchedules;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<DoctorClinic> doctorClinics;

    @OneToOne
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;
}
