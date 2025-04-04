package com.fg.clinicservice.clinic_service.model;

import com.fg.clinicservice.clinic.model.Clinic;
import com.fg.clinicservice.service.model.EService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clinic_service")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicService {
    @EmbeddedId
    private ClinicServiceId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("clinicId")
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("serviceId")
    @JoinColumn(name = "service_id", nullable = false)
    private EService service;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private Status status = Status.ACTIVE;

    public enum Status {
        ACTIVE,
        INACTIVE,
    }
}
