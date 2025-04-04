package com.fg.clinicservice.clinic_service.service;

import com.fg.clinicservice.clinic_service.model.ClinicService;
import com.fg.clinicservice.clinic_service.model.ClinicServiceDto;
import com.fg.clinicservice.clinic_service.model.ClinicServiceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClinicServiceRepository extends JpaRepository<ClinicService, ClinicServiceId> {
    List<ClinicService> findAll ();
    List<ClinicService> findByStatus(ClinicService.Status status);
    List<ClinicService> findByClinic_ClinicId(UUID clinicClinicId);
    List<ClinicService> findByService_ServiceId(UUID serviceServiceId);
    Optional<ClinicService> findById(ClinicServiceId clinicServiceId);
}
