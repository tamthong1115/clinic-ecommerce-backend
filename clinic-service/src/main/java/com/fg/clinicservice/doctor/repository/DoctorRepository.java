package com.fg.clinicservice.doctor.repository;

import com.fg.clinicservice.doctor.model.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID>, JpaSpecificationExecutor<Doctor> {
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByLicenseNumber(String licenseNumber);
    List<Doctor> findByGender(String gender);
    List<Doctor> findByExperienceYearsGreaterThanEqual(Integer experienceYears);
    List<Doctor> findByEducationContaining(String education);

    @Query("SELECT DISTINCT d FROM Doctor d " +
            "LEFT JOIN d.specialities s " +
            "LEFT JOIN d.doctorSchedules ds " +
            "WHERE (:clinicId IS NULL OR d.clinic.clinicId = :clinicId) " +
            "AND (:specialityIds IS NULL OR s.specialityId IN :specialityIds) " +
            "AND (:doctorName IS NULL OR CONCAT(d.firstName, ' ', d.lastName) LIKE %:doctorName%) " +
            "AND (:availableFrom IS NULL OR :availableTo IS NULL OR " +
            "     EXISTS (SELECT 1 FROM DoctorSchedule schedule WHERE schedule.doctor = d " +
            "             AND schedule.startTime >= :availableFrom " +
            "             AND schedule.endTime <= :availableTo " +
            "             AND schedule.isActive = true)) ")
    Page<Doctor> searchDoctors(
            @Param("clinicId") UUID clinicId,
            @Param("specialityIds") Set<UUID> specialityIds,
            @Param("doctorName") String doctorName,
            @Param("availableFrom") LocalDateTime availableFrom,
            @Param("availableTo") LocalDateTime availableTo,
            Pageable pageable);

    Optional<Doctor> findByUserId(UUID userId);
}
