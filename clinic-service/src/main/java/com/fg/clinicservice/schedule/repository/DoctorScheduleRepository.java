package com.fg.clinicservice.schedule.repository;

import com.fg.clinicservice.schedule.model.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, UUID> {

    List<DoctorSchedule> findByDoctorId(UUID doctorId);
    List<DoctorSchedule> findByClinicId(UUID clinicId);
    List<DoctorSchedule> findByDoctorIdAndIsActiveTrue(UUID doctorId);
    List<DoctorSchedule> findByDoctorIdAndDayOfWeekAndIsActiveTrue(UUID doctorId, DayOfWeek dayOfWeek);
} 