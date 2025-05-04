package com.fg.clinicservice.clinic.service;

import com.fg.clinicservice.clinic.model.ClinicOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClinicOwnerRepository extends JpaRepository<ClinicOwner, UUID> {
    ClinicOwner findByUserId(UUID userId);
}
