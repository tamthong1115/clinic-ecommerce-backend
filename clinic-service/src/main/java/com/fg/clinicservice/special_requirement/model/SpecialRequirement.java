package com.fg.clinicservice.special_requirement.model;


import com.fg.clinicservice.service.model.EService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "special_requirement")
@Data
@NoArgsConstructor
public class SpecialRequirement {
    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private EService service;

    @NotNull
    @Column(name = "service_id", nullable = false, updatable = false, insertable = false)
    private UUID serviceId;

    @NotNull
    @Column(nullable = false)
    private String requirement;
}
