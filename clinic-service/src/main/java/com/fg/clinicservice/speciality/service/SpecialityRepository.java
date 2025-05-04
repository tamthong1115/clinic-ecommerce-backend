package com.fg.clinicservice.speciality.service;

import com.fg.clinicservice.speciality.model.Speciality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpecialityRepository extends JpaRepository<Speciality, UUID> {
    @Override
    Optional<Speciality> findById(UUID uuid);
    Page<Speciality> findAll(Pageable pageable);
}
