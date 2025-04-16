package com.fg.doctorservice.doctor;

import com.fg.doctorservice.doctor.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByLicenseNumber(String licenseNumber);
    List<Doctor> findByGender(String gender);
    List<Doctor> findByExperienceYearsGreaterThanEqual(Integer experienceYears);
    List<Doctor> findByEducationContaining(String education);
}
