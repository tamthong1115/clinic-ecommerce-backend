package com.fg.appointment_service.doctor.repository;

import com.fg.appointment_service.doctor.model.DoctorClinic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DoctorClinicRepository extends JpaRepository<DoctorClinic, UUID> {

}
