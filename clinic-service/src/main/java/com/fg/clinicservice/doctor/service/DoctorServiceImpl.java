package com.fg.clinicservice.doctor.service;


import com.fg.clinicservice.client.user.AuthClient;
import com.fg.clinicservice.client.user.RegisterRequestWithRoleDTO;
import com.fg.clinicservice.client.user.Role;
import com.fg.clinicservice.client.user.UserDTO;
import com.fg.clinicservice.doctor.DoctorMapper;
import com.fg.clinicservice.doctor.dto.DoctorBasicResponse;
import com.fg.clinicservice.doctor.dto.DoctorDetailResponse;
import com.fg.clinicservice.doctor.dto.DoctorRequest;
import com.fg.clinicservice.doctor.model.Doctor;
import com.fg.clinicservice.clinic.model.Clinic;
import com.fg.clinicservice.doctor.repository.DoctorRepository;
import com.fg.clinicservice.exception.ResourceNotFoundException;
import com.fg.clinicservice.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final AuthClient authClient;

    private final static String DEFAULT_PASSWORD = "Doctor@123";
    private final static String ROLE_DOCTOR = Role.DOCTOR.name();

    @Override
    @Transactional
    public DoctorDetailResponse createDoctor(UUID ClinicId, DoctorRequest doctorRequest) {
        ResponseEntity<Boolean> emailCheckResponse = authClient.checkEmailExists(doctorRequest.getEmail());
        if (Boolean.TRUE.equals(emailCheckResponse.getBody())) {
            throw new UserAlreadyExistsException("Email already exists: " + doctorRequest.getEmail());
        }

        RegisterRequestWithRoleDTO registerRequest = RegisterRequestWithRoleDTO.builder()
                .email(doctorRequest.getEmail())
                .password(DEFAULT_PASSWORD)
                .role(ROLE_DOCTOR)
                .build();

        ResponseEntity<UserDTO> userResponse = authClient.createUser(registerRequest);
        if(userResponse.getBody() == null) {
            throw new RuntimeException("Failed to create user account");
        }

        Doctor doctor = doctorMapper.toEntity(doctorRequest);

        doctor.setUserId(userResponse.getBody().getUserId());

        if (ClinicId != null) {
            Clinic clinic = new Clinic();
            clinic.setClinicId(ClinicId);
            doctor.setClinic(clinic);
        }
        Doctor savedDoctor = doctorRepository.save(doctor);

        return doctorMapper.toDetailResponse(savedDoctor);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorDetailResponse getDoctorById(UUID id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        return doctorMapper.toDetailResponse(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorBasicResponse> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toBasicResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DoctorDetailResponse updateDoctor(UUID id, DoctorRequest doctorRequest) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        
        doctorMapper.updateEntity(doctor, doctorRequest);
        Doctor updatedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toDetailResponse(updatedDoctor);
    }

    @Override
    @Transactional
    public void deleteDoctor(UUID id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }

    @Override
    @Transactional()
    public UserDTO getCurrentUser() {
        return authClient.getCurrentUser().getBody();
    }

}
