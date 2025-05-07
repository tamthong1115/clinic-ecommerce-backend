package com.fg.clinicservice.doctor;


import com.fg.clinicservice.doctor.dto.DoctorBasicResponse;
import com.fg.clinicservice.doctor.dto.DoctorCertificationDTO;
import com.fg.clinicservice.doctor.dto.DoctorDetailResponse;
import com.fg.clinicservice.doctor.dto.DoctorRequest;
import com.fg.clinicservice.doctor.model.Doctor;
import com.fg.clinicservice.doctor.model.DoctorCertification;
import com.fg.clinicservice.schedule.dto.DoctorScheduleDTO;
import com.fg.clinicservice.schedule.model.DoctorSchedule;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.Collections;
@Component
public class DoctorMapper {

    public DoctorBasicResponse toBasicResponse(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        DoctorBasicResponse response = new DoctorBasicResponse();
        response.setId(doctor.getId());
        response.setFirstName(doctor.getFirstName());
        response.setLastName(doctor.getLastName());
        response.setEmail(doctor.getEmail());
        response.setPhone(doctor.getPhone());
        response.setGender(doctor.getGender());
        response.setProfilePicture(doctor.getProfilePicture());

        return response;
    }

    public DoctorDetailResponse toDetailResponse(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        DoctorDetailResponse response = new DoctorDetailResponse();
        response.setId(doctor.getId());
        response.setFirstName(doctor.getFirstName());
        response.setLastName(doctor.getLastName());
        response.setEmail(doctor.getEmail());
        response.setPhone(doctor.getPhone());
        response.setGender(doctor.getGender());
        response.setProfilePicture(doctor.getProfilePicture());
        response.setExperienceYears(doctor.getExperienceYears());
        response.setEducation(doctor.getEducation());

        if (doctor.getDoctorCertifications() != null) {
            response.setCertifications(doctor.getDoctorCertifications().stream()
                    .map(this::toCertificationDTO)
                    .collect(Collectors.toList()));
        }

        if (doctor.getDoctorSchedules() != null) {
            response.setSchedules(doctor.getDoctorSchedules().stream()
                    .map(this::toScheduleDTO)
                    .collect(Collectors.toList()));
        }

        if (doctor.getClinic() != null) {
            response.setClinicIds(doctor.getClinic().getClinicId());
        }

        return response;
    }

    public Doctor toEntity(DoctorRequest request) {
        if (request == null) {
            return null;
        }

        Doctor doctor = new Doctor();
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());
        doctor.setGender(request.getGender());
        doctor.setProfilePicture(request.getProfilePicture());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setEducation(request.getEducation());

        return doctor;
    }

    private DoctorCertificationDTO toCertificationDTO(DoctorCertification certification) {
        if (certification == null) {
            return null;
        }

        DoctorCertificationDTO dto = new DoctorCertificationDTO();
        dto.setId(certification.getId());
        dto.setCertificationName(certification.getCertificationName());
        dto.setCertificationNumber(certification.getCertificationNumber());
        dto.setSpecialty(certification.getSpecialty());
        dto.setIssuedBy(certification.getIssuedBy());
        dto.setStatus(certification.getStatus());
        dto.setCertificateImageUrl(certification.getCertificateImageUrl());
        dto.setIssueDate(certification.getIssueDate());
        dto.setExpiryDate(certification.getExpiryDate());

        return dto;
    }

    public DoctorScheduleDTO toScheduleDTO(DoctorSchedule schedule) {
        if (schedule == null) {
            return null;
        }

        DoctorScheduleDTO dto = new DoctorScheduleDTO();
        dto.setId(schedule.getId());
        dto.setClinicId(schedule.getClinicId());
        dto.setDayOfWeek(schedule.getDayOfWeek());
        dto.setStartTime(schedule.getStartTime());
        dto.setEndTime(schedule.getEndTime());

        return dto;
    }

    public void updateEntity(Doctor doctor, DoctorRequest request) {
        if (doctor == null || request == null) {
            return;
        }

        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());
        doctor.setGender(request.getGender());
        doctor.setProfilePicture(request.getProfilePicture());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setEducation(request.getEducation());
    }
}
