package com.fg.doctorservice.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, UUID> {
    List<DoctorSchedule> findByDoctorId(UUID doctorId);
    List<DoctorSchedule> findByClinicId(UUID clinicId);
    List<DoctorSchedule> findByDoctorIdAndDayOfWeek(UUID doctorId, String dayOfWeek);
    List<DoctorSchedule> findByClinicIdAndDayOfWeek(UUID clinicId, String dayOfWeek);
} 