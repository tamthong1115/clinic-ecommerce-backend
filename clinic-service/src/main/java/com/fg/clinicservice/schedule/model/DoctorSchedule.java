package com.fg.clinicservice.schedule.model;

import com.fg.clinicservice.doctor.model.Doctor;
import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "doctor_schedule")
public class DoctorSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "clinic_id")
    private UUID clinicId;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "slot_duration_minutes", nullable = false)
    private Integer slotDurationMinutes = 60; // Default 1 hour slots

    @Column(name = "break_minutes_between_slots")
    private Integer breakMinutesBetweenSlots = 0; // Default no break

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}