package com.fg.appointment.repository;

import com.fg.appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByDoctorIdAndAppointmentDate(UUID doctorId, LocalDate appointmentDate);
    List<Appointment> findByPatientId(UUID patientId);
    List<Appointment> findByDoctorIdAndAppointmentDateBetween(UUID doctorId, LocalDate startDate, LocalDate endDate);

    boolean existsByDoctorId(UUID doctorId);
}