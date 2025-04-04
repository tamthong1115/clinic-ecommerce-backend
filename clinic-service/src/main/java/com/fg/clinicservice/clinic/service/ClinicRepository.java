package com.fg.clinicservice.clinic.service;

import com.fg.clinicservice.clinic.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClinicRepository extends JpaRepository<Clinic, UUID> {
    List<Clinic> findAll();
    Optional<Clinic> findById(UUID uuid);

    @Modifying
    @Query("UPDATE Clinic c SET c.status= :status WHERE c.clinicId= :clinicId")
    void updateStatus(@Param("clinicId") UUID clinicId, @Param("status") Clinic.Status status);
}
