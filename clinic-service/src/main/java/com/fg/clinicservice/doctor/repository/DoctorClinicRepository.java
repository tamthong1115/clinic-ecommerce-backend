package com.fg.clinicservice.doctor.repository;

import com.fg.clinicservice.doctor.model.DoctorClinic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DoctorClinicRepository extends JpaRepository<DoctorClinic, UUID> {

}
