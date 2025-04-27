package com.fg.doctorservice.doctor.repository;

import com.fg.doctorservice.doctor.model.DoctorClinic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DoctorClinicRepository extends JpaRepository<DoctorClinic, UUID> {

}
