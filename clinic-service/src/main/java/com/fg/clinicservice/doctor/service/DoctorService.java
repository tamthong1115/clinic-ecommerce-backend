package com.fg.clinicservice.doctor.service;


import com.fg.clinicservice.client.user.UserDTO;
import com.fg.clinicservice.doctor.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface DoctorService {
    DoctorDetailResponse createDoctor(UUID clinicId, DoctorRequest doctorRequest);
    DoctorDetailResponse getDoctorById(UUID id);
    List<DoctorBasicResponse> getAllDoctors();
    DoctorDetailResponse updateDoctor(UUID id, DoctorRequest doctorRequest);
    void deleteDoctor(UUID id);
    UserDTO getCurrentUser();
    UUID getDoctorIdByUserId(UUID userId);
    Page<DoctorSearchResponse> searchDoctors(DoctorSearchCriteria criteria);
    Page<DoctorBasicResponse> getAllDoctorsBasic(int page, int size, String sortBy, String direction);
    Page<DoctorBasicResponse> getDoctorsByClinicBasic(UUID clinicId, int page, int size, String sortBy, String direction);
    Page<DoctorBasicResponse> searchDoctorsBasic(DoctorSearchCriteria criteria);
}
