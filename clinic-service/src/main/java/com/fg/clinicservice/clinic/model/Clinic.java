package com.fg.clinicservice.clinic.model;

import com.fg.clinicservice.clinic_service.model.ClinicService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.*;

@Entity
@Table(name = "clinic")
@Data
@NoArgsConstructor
public class Clinic {
    @Id
    @UuidGenerator
    @Column(name = "clinic_id")
    private UUID clinicId;

    @NotNull
    @Column(name = "owner",nullable = false)
    private UUID userId;

    @NotNull
    @Column(name = "owner_name", nullable = false)
    private String userName;

    @NotNull
    @Column( name = "name",nullable = false)
    private String clinicName;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "phone",nullable = false)
    private String clinicPhone;

    @NotNull
    @Column(name = "address",nullable = false, unique = true)
    private String clinicAddress;

    @NotNull
    private String description;

    @Column(name = "image", nullable = true)
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private Status status = Status.CLOSED;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClinicService> clinicServices = new HashSet<>();

    public enum Status {
        CLOSED,
        OPEN
    }
}
