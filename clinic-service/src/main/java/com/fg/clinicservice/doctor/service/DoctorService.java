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
    Page<DoctorSearchResponse> searchDoctors(DoctorSearchCriteria criteria);

}
