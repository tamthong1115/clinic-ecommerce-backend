package com.fg.clinicservice.doctor.service;


import com.fg.clinicservice.client.user.AuthClient;
import com.fg.clinicservice.client.user.RegisterRequestWithRoleDTO;
import com.fg.clinicservice.client.user.Role;
import com.fg.clinicservice.client.user.UserDTO;
import com.fg.clinicservice.doctor.DoctorMapper;
import com.fg.clinicservice.doctor.dto.*;
import com.fg.clinicservice.doctor.model.Doctor;
import com.fg.clinicservice.clinic.model.Clinic;
import com.fg.clinicservice.doctor.repository.DoctorRepository;
import com.fg.clinicservice.exception.ResourceNotFoundException;
import com.fg.clinicservice.exception.UserAlreadyExistsException;
import com.fg.clinicservice.schedule.model.DoctorSchedule;
import com.fg.clinicservice.service_clinic.model.EService;
import com.fg.clinicservice.speciality.model.Speciality;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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


    @Override
    @Transactional(readOnly = true)
    public Page<DoctorSearchResponse> searchDoctors(DoctorSearchCriteria criteria) {
        // Create pageable object for pagination and sorting
        Pageable pageable = PageRequest.of(
                criteria.getPage(),
                criteria.getSize(),
                Sort.by(criteria.getSortDirection(), criteria.getSortBy())
        );

        // Build specification based on search criteria
        Specification<Doctor> spec = Specification.where(null);

        if (criteria.getName() != null && !criteria.getName().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.or(
                            cb.like(cb.lower(root.get("firstName")), "%" + criteria.getName().toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("lastName")), "%" + criteria.getName().toLowerCase() + "%")
                    )
            );
        }

        if (criteria.getClinicId() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("clinic").get("id"), criteria.getClinicId())
            );
        }

        if (criteria.getSpecialityId() != null) {
            spec = spec.and((root, query, cb) -> {
                Join<Doctor, Speciality> specialities = root.join("specialities", JoinType.INNER);
                return cb.equal(specialities.get("id"), criteria.getSpecialityId());
            });
        }

        if (criteria.getServiceId() != null) {
            spec = spec.and((root, query, cb) -> {
                Join<Doctor, EService> services = root.join("services", JoinType.INNER);
                return cb.equal(services.get("id"), criteria.getServiceId());
            });
        }

        if (criteria.getMinExperience() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("experienceYears"), criteria.getMinExperience())
            );
        }

        if (criteria.getMaxExperience() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("experienceYears"), criteria.getMaxExperience())
            );
        }

        if (criteria.getMinPrice() != null) {
            spec = spec.and((root, query, cb) -> {
                Join<Doctor, EService> services = root.join("services", JoinType.INNER);
                return cb.greaterThanOrEqualTo(services.get("price"), criteria.getMinPrice());
            });
        }

        if (criteria.getMaxPrice() != null) {
            spec = spec.and((root, query, cb) -> {
                Join<Doctor, EService> services = root.join("services", JoinType.INNER);
                return cb.lessThanOrEqualTo(services.get("price"), criteria.getMaxPrice());
            });
        }

        if (criteria.getDayOfWeek() != null && !criteria.getDayOfWeek().isEmpty()) {
            spec = spec.and((root, query, cb) -> {
                Join<Doctor, DoctorSchedule> schedules = root.join("doctorSchedules", JoinType.INNER);
                return cb.equal(schedules.get("dayOfWeek"), criteria.getDayOfWeek());
            });
        }

        // To eliminate duplicates due to joins
        spec = spec.and((root, query, cb) -> {
            query.distinct(true);
            return cb.conjunction();
        });
        // Execute the query with the specification and pageable
        Page<Doctor> doctorsPage = doctorRepository.findAll(spec, pageable);

        // Map results to DTOs
        return doctorsPage.map(doctorMapper::toSearchResponse);
    }

}
