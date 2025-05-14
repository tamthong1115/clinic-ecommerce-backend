package com.fg.clinicservice.doctor.service;

import com.fg.clinicservice.service_clinic.model.EService;
import com.fg.clinicservice.client.appointment.AppointmentClient;
import com.fg.clinicservice.client.appointment.TimeSlotDTO;
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
import com.fg.clinicservice.util.CloudinaryService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.criteria.Predicate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final AuthClient authClient;
    private final CloudinaryService cloudinaryService;

    private final static String DEFAULT_PASSWORD = "Doctor@123";
    private final static String ROLE_DOCTOR = Role.DOCTOR.name();
    private final AppointmentClient appointmentClient;

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
        if (userResponse.getBody() == null) {
            throw new RuntimeException("Failed to create user account");
        }

        Doctor doctor = doctorMapper.toEntity(doctorRequest);
        String defaultProfilePicture = null;
        if (doctorRequest.getGender().toUpperCase() == "MALE") {
            defaultProfilePicture = "https://res.cloudinary.com/dulzekaen/image/upload/v1746699451/flat-style-vector-illustration-medical-illustrator_1033579-58110_grznrx.avif";
        } else if (doctorRequest.getGender().toUpperCase() == "FEMALE") {
            defaultProfilePicture = "https://res.cloudinary.com/dulzekaen/image/upload/v1746700376/istockphoto-1190555586-1024x1024_wucd29.jpg";
        } else {
            defaultProfilePicture = "https://res.cloudinary.com/dulzekaen/image/upload/v1746700456/images_s1mhzv.png";
        }


        if (doctorRequest.getProfilePicture() != null && !doctorRequest.getProfilePicture().isEmpty()) {
            try {
                // Delete existing profile picture if present
                if (doctor.getProfilePicture() != null && !doctor.getProfilePicture().isEmpty()) {
                    cloudinaryService.deleteImage(doctor.getProfilePicture());
                }

                // Upload new profile picture and get URL
                defaultProfilePicture = cloudinaryService.uploadDoctorProfilePicture(doctorRequest.getProfilePicture());
            } catch (Exception e) {
                throw new RuntimeException("Failed to upload doctor profile picture", e);
            }
        }

        doctor.setUserId(userResponse.getBody().getUserId());
        doctor.setProfilePicture(defaultProfilePicture);

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
    public UUID getDoctorIdByUserId(UUID userId) {
        return doctorRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found for user ID: " + userId))
                .getId();
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
                    cb.equal(root.get("clinic").get("clinicId"), criteria.getClinicId())
            );
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



        return doctorsPage.map(doctor -> {
            DoctorSearchResponse response = doctorMapper.toSearchResponse(doctor);
            // Prepare full weekly schedule based on next 7 days
            List<DoctorSearchResponse.ScheduleInfo> finalSchedules = new ArrayList<>();

            // Loop through next 7 days
            for (int i = 0; i < 7; i++) {
                LocalDate date = LocalDate.now().plusDays(i);
                DayOfWeek dayOfWeek = date.getDayOfWeek();

                // Filter doctor's schedules that match this day of week
                List<DoctorSchedule> dailySchedules = doctor.getDoctorSchedules().stream()
                        .filter(s -> s.getDayOfWeek() == dayOfWeek)
                        .collect(Collectors.toList());

                List<TimeSlotDTO> bookedTimeSlots = appointmentClient.getBookedTimeSlots(doctor.getUserId(), date);

                for (DoctorSchedule schedule : dailySchedules) {
                    DoctorSearchResponse.ScheduleInfo scheduleInfo = new DoctorSearchResponse.ScheduleInfo();
                    scheduleInfo.setId(schedule.getId());
                    scheduleInfo.setDate(date);
                    scheduleInfo.setStartTime(schedule.getStartTime());
                    scheduleInfo.setEndTime(schedule.getEndTime());

                    boolean isBooked = bookedTimeSlots.stream().anyMatch(booked ->
                            !(booked.getEndTime().isBefore(schedule.getStartTime()) || booked.getStartTime().isAfter(schedule.getEndTime()))
                    );


                    scheduleInfo.setBooked(isBooked);
                    finalSchedules.add(scheduleInfo);
                }
            }

            response.setSchedules(finalSchedules);
            return response;
        });
    }

    @Override
    public Page<DoctorBasicResponse> searchDoctorsBasic(DoctorSearchCriteria criteria) {
        // Build the specification for filtering based on criteria
        Specification<Doctor> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getName() != null && !criteria.getName().isEmpty()) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("firstName")),
                                "%" + criteria.getName().toLowerCase() + "%"
                        ),
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("lastName")),
                                "%" + criteria.getName().toLowerCase() + "%"
                        )
                ));
            }

            if (criteria.getClinicId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("clinic").get("clinicId"), criteria.getClinicId()));
            }


            if (criteria.getServiceName() != null && !criteria.getServiceName().isEmpty()) {
                Join<Doctor, EService> serviceJoin = root.join("services", JoinType.INNER);
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(serviceJoin.get("name")),
                        "%" + criteria.getServiceName().toLowerCase() + "%"
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // Create pageable object
        Pageable pageable = PageRequest.of(
                criteria.getPage(),
                criteria.getSize(),
                Sort.by(criteria.getSortDirection(), criteria.getSortBy())
        );

        // Query the database
        Page<Doctor> doctorPage = doctorRepository.findAll(specification, pageable);

        return doctorPage.map(doctorMapper::toBasicResponse);
    }

    @Override
    public Page<DoctorBasicResponse> getAllDoctorsBasic(int page, int size, String sortBy, String direction) {


        Sort sort = direction.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return doctorRepository.findAll(pageable)
                .map(doctorMapper::toBasicResponse);
    }

    @Override
    public Page<DoctorBasicResponse> getDoctorsByClinicBasic(UUID clinicId, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Doctor> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("clinic").get("clinicId"), clinicId);

        return doctorRepository.findAll(spec, pageable)
                .map(doctorMapper::toBasicResponse);
    }


}
