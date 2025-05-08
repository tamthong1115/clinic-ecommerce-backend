package com.fg.clinicservice.doctor.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class DoctorSearchResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private Integer experienceYears;
    private String education;

    // Service information
    private Set<ServiceInfo> services;

    // Schedule information
    private List<ScheduleInfo> schedules;

    // Clinic information
    private ClinicInfo clinic;

    @Data
    public static class ServiceInfo {
        private UUID id;
        private String name;
        private BigDecimal price;
    }

    @Data
    public static class ScheduleInfo {
        private UUID id;
        private DayOfWeek dayOfWeek;
        private LocalTime startTime;
        private LocalTime endTime;
    }

    @Data
    public static class ClinicInfo {
        private UUID id;
        private String name;
        private String address;
    }
}