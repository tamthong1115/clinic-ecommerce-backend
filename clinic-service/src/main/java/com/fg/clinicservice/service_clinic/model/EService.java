package com.fg.clinicservice.service_clinic.model;

import com.fg.clinicservice.clinic_service.model.ClinicService;
import com.fg.clinicservice.speciality.model.Speciality;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.*;

@Entity
@Table(name = "service")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EService {
    @Id
    @UuidGenerator
    @Column(name = "id",nullable = false,unique = true)
    private UUID serviceId;

    @NotNull
    @Column(name = "service_name",nullable = false)
    private String serviceName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;

    @NotNull
    @Column(nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false , precision = 10 , scale = 2)
    private BigDecimal price;

    @NotNull
    @Column(nullable = false)
    private LocalTime duration;

    @NotNull
    @Column(name = "requires_prescription", nullable = false)
    private boolean requiresPrescription;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;

    @Builder.Default
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClinicService> clinicServices = new HashSet<>();

}
