package com.fg.clinicservice.speciality.model;

import com.fg.clinicservice.service.model.EService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Speciality")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Speciality {
    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID specialityId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String specialityName;

    @NotNull
    @Column(name = "description",nullable = false)
    private String specialityDescription;

    @NotNull
    @Column(name = "image_url",nullable = false)
    private String specialityImageUrl;

    @OneToMany(mappedBy = "speciality", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EService> eService = new HashSet<>();
}
