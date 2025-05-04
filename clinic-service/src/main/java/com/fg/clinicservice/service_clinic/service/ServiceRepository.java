package com.fg.clinicservice.service_clinic.service;

import com.fg.clinicservice.service_clinic.model.EService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<EService, UUID> {
    Optional<EService> findById(UUID uuid);
    List<EService> findAll();

    @Modifying
    @Query("UPDATE EService s SET s.active = :active WHERE s.serviceId = :serviceId")
    void updateServiceActiveStatus(@Param("serviceId") UUID serviceId, @Param("active") boolean active);

    @Query("SELECT s FROM EService s WHERE s.speciality.specialityId = :specialityId")
    List<EService> findAllBySpecialityId(@Param("specialityId") UUID specialityId);

}
