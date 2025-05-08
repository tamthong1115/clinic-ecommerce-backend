package com.fg.clinicservice.doctor.model;

import com.fg.clinicservice.clinic.model.Clinic;
import com.fg.clinicservice.schedule.model.DoctorSchedule;
import com.fg.clinicservice.service_clinic.model.EService;
import com.fg.clinicservice.service_clinic.service.IService;
import com.fg.clinicservice.speciality.model.Speciality;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @ManyToMany
    @JoinTable(
            name = "doctor_speciality",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    private Set<Speciality> specialities = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "doctor_service",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<EService> services = new HashSet<>();
}
