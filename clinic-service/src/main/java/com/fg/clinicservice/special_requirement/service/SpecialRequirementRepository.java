package com.fg.clinicservice.special_requirement.service;

import com.fg.clinicservice.special_requirement.model.SpecialRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpecialRequirementRepository extends JpaRepository<SpecialRequirement, UUID> {
    Optional<SpecialRequirement> findById(UUID id);
    List<SpecialRequirement> findByServiceId( UUID serviceId);
    void deleteByServiceId( UUID serviceId);
}
