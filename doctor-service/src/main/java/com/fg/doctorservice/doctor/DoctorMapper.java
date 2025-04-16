package com.fg.doctorservice.doctor;

import com.fg.doctorservice.doctor.dto.DoctorBasicResponse;
import com.fg.doctorservice.doctor.dto.DoctorDetailResponse;
import com.fg.doctorservice.doctor.dto.DoctorRequest;
import com.fg.doctorservice.doctor.dto.DoctorCertificationDTO;
import com.fg.doctorservice.doctor.dto.DoctorScheduleDTO;
import com.fg.doctorservice.doctor.model.Doctor;
import com.fg.doctorservice.doctor.model.DoctorCertification;
import com.fg.doctorservice.doctor.model.DoctorSchedule;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DoctorMapper {

    public DoctorBasicResponse toBasicResponse(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        DoctorBasicResponse response = new DoctorBasicResponse();
        response.setId(doctor.getId());
        response.setName(doctor.getName());
        response.setEmail(doctor.getEmail());
        response.setPhone(doctor.getPhone());
        response.setGender(doctor.getGender());
        response.setProfilePicture(doctor.getProfile_picture());

        return response;
    }

    public DoctorDetailResponse toDetailResponse(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        DoctorDetailResponse response = new DoctorDetailResponse();
        response.setId(doctor.getId());
        response.setName(doctor.getName());
        response.setEmail(doctor.getEmail());
        response.setPhone(doctor.getPhone());
        response.setGender(doctor.getGender());
        response.setProfilePicture(doctor.getProfile_picture());
        response.setExperienceYears(doctor.getExperience_years());
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

        if (doctor.getDoctorClinics() != null) {
            response.setClinicIds(doctor.getDoctorClinics().stream()
                    .map(dc -> dc.getClinicId())
                    .collect(Collectors.toList()));
        }

        return response;
    }

    public Doctor toEntity(DoctorRequest request) {
        if (request == null) {
            return null;
        }

        Doctor doctor = new Doctor();
        doctor.setName(request.getName());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());
        doctor.setGender(request.getGender());
        doctor.setProfile_picture(request.getProfilePicture());
        doctor.setExperience_years(request.getExperienceYears());
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
        dto.setDayOfWeek(schedule.getDay_of_week());
        dto.setStartTime(schedule.getStart_time());
        dto.setEndTime(schedule.getEnd_time());

        return dto;
    }

    public void updateEntity(Doctor doctor, DoctorRequest request) {
        if (doctor == null || request == null) {
            return;
        }

        doctor.setName(request.getName());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());
        doctor.setGender(request.getGender());
        doctor.setProfile_picture(request.getProfilePicture());
        doctor.setExperience_years(request.getExperienceYears());
        doctor.setEducation(request.getEducation());
    }
}
