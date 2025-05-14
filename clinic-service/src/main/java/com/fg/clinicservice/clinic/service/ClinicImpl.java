package com.fg.clinicservice.clinic.service;

import com.fg.clinicservice.client.user.RegisterRequestWithRoleDTO;
import com.fg.clinicservice.client.user.UserDTO;
import com.fg.clinicservice.clinic.dto.ClinicOwnerDTO;
import com.fg.clinicservice.clinic.dto.CreateClinicOwnerRequest;
import com.fg.clinicservice.clinic.model.*;
import com.fg.clinicservice.clinic.dto.ClinicDTO;
import com.fg.clinicservice.client.user.AuthClient;
import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.response.ResponseError;
import com.fg.clinicservice.util.CloudinaryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClinicImpl implements IClinicService {
    @Autowired
    CloudinaryService cloudinaryService;

    private final ClinicRepository clinicRepository;
    private final ClinicOwnerRepository clinicOwnerRepository;
    private final AuthClient authClient;
    private final HttpServletRequest httpServletRequest;

    private static final String DEFAULT_PASSWORD = "ClinicOwner@123";
    private static final String ROLE_CLINIC_OWNER = "CLINIC_OWNER";


    public ClinicImpl(ClinicRepository clinicRepository, ClinicOwnerRepository clinicOwnerRepository, AuthClient authClient, HttpServletRequest httpServletRequest) {
        this.clinicRepository = clinicRepository;
        this.clinicOwnerRepository = clinicOwnerRepository;
        this.authClient = authClient;
        this.httpServletRequest = httpServletRequest;
    }


    public ResponseData<ClinicOwnerDTO> createClinicOwner(CreateClinicOwnerRequest request) {
        // Check if email exists
        ResponseEntity<Boolean> emailCheckResponse = authClient.checkEmailExists(request.getEmail());
        if (Boolean.TRUE.equals(emailCheckResponse.getBody())) {
            throw new RuntimeException("Email already exists in the system");
        }

        // Create user account
        RegisterRequestWithRoleDTO userRequest = RegisterRequestWithRoleDTO.builder()
                .email(request.getEmail())
                .password(DEFAULT_PASSWORD)
                .role(ROLE_CLINIC_OWNER)
                .build();

        ResponseEntity<UserDTO> userResponse = authClient.createUser(userRequest);
        if (userResponse.getBody() == null) {
            throw new RuntimeException("Failed to create user account");
        }

        // Create clinic owner using mapper
        ClinicOwner clinicOwner = ClinicOwnerMapper.fromCreateRequest(
                userResponse.getBody().getUserId(), request
        );

        ClinicOwner savedOwner = clinicOwnerRepository.save(clinicOwner);

        // Convert to DTO using mapper and wrap in ResponseData
        ClinicOwnerDTO ownerDTO = ClinicOwnerMapper.toDto(savedOwner);
        return new ResponseData<>(201, "Clinic owner created successfully", ownerDTO);
    }

    @Override
    public ResponseData<ClinicDTO> createNewClinic(ClinicForm clinicForm) {
        UserDTO user = authClient.getCurrentUser().getBody();
        ClinicOwner clinicOwner = clinicOwnerRepository.findByUserId(user.getUserId());

        //tao clinic
        Clinic createClinic = ClinicMapper.fromForm(clinicForm);
        createClinic.setEmail(user.getEmail());
        createClinic.setOwner(clinicOwner);

        //Upload anh
        if (clinicForm.getFile() != null && !clinicForm.getFile().isEmpty()) {
            List<String> upLoadImageurls = cloudinaryService.uploadClinicRoomImage(clinicForm.getFile());
            createClinic.setImages(upLoadImageurls);
        }

        //luu va tra kq
        Clinic savedClinic = clinicRepository.save(createClinic);
        ClinicDTO clinicDto = ClinicMapper.toDto(savedClinic);

        return new ResponseData<>(201, "clinic created successfully", clinicDto);
    }

    @Override
    public ResponseData<Page<ClinicDTO>> getAllClinics(int page, int size, String sortBy, String direction) {
        // Create Sort object based on direction
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        // Create Pageable object
        Pageable pageable = PageRequest.of(page, size, sort);

        // Get page of clinics from repository
        Page<Clinic> clinicPage = clinicRepository.findAll(pageable);

        // Map entities to DTOs
        Page<ClinicDTO> clinicDTOPage = clinicPage.map(ClinicMapper::toDto);

        return new ResponseData<>(200, "Clinics retrieved successfully", clinicDTOPage);
    }

    @Override
    public ResponseData<ClinicDTO> updateClinic(UUID clinicId, ClinicForm clinicForm) {
        Clinic existingClinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new RuntimeException("Clinic not found"));

        List<String> oldImages = existingClinic.getImages() != null ? existingClinic.getImages() : new ArrayList<>();
        List<String> keptImages = clinicForm.getImage() != null ? clinicForm.getImage() : new ArrayList<>();


        List<String> deletedImages = oldImages.stream()
                .filter(img -> !keptImages.contains(img))
                .toList();

        deletedImages.forEach(cloudinaryService::deleteImage);
        List<String> newImageUrls = new ArrayList<>(keptImages);
        if (clinicForm.getFile() != null && !clinicForm.getFile().isEmpty()) {
            List<String> upLoadImageUrls = cloudinaryService.uploadClinicRoomImage(clinicForm.getFile());
            newImageUrls.addAll(upLoadImageUrls);
        }

        existingClinic.setImages(newImageUrls);
        ClinicMapper.updateClinicForm(existingClinic, clinicForm);

        // Update owner if ownerId is provided
        if (clinicForm.getOwnerId() != null) {
            ClinicOwner owner = clinicOwnerRepository.findById(clinicForm.getOwnerId())
                    .orElseThrow(() -> new RuntimeException("Owner not found"));
            existingClinic.setOwner(owner);
        }

        Clinic updatedClinic = clinicRepository.save(existingClinic);
        ClinicDTO clinicDto = ClinicMapper.toDto(updatedClinic);
        return new ResponseData<>(200, "clinic updated successfully", clinicDto);
    }

    @Override
    public ResponseData<ClinicDTO> getClinicById(UUID clinicId) {
        Clinic clinic = clinicRepository.findById(clinicId).orElseThrow(() -> new RuntimeException("clinic not found"));
        ClinicDTO clinicDto = ClinicMapper.toDto(clinic);
        return new ResponseData<>(200, "clinic get successfully", clinicDto);
    }


    @Override
    @Transactional
    public ResponseData<String> updateClinicStatus(UUID clinicId, Clinic.Status status) {
        try {
            Clinic clinic = clinicRepository.findById(clinicId)
                    .orElseThrow(() -> new RuntimeException("Clinic not found"));

            clinic.setStatus(status);
            clinicRepository.save(clinic);

            return new ResponseData<>(200, "Clinic status updated successfully", "Updated to: " + status);
        } catch (IllegalArgumentException e) {
            return new ResponseError<>(400, "Invalid status value: " + status);
        } catch (Exception e) {
            return new ResponseError<>(500, "An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseData<List<ClinicDTO>> getClinicsByOwnerId() {
        ResponseEntity<UserDTO> currentUser = authClient.getCurrentUser();
        ClinicOwner clinicOwner = clinicOwnerRepository.findByUserId(currentUser.getBody().getUserId());
        List<ClinicDTO> listClinic = clinicRepository.findByOwner_OwnerId(clinicOwner.getOwnerId()).stream()
                .map(ClinicMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseData<>(200, "clinic get successfully", listClinic);
    }


    @Override
    public ResponseData<Page<ClinicDTO>> getClinicsByOwnerIdWithPagination(int page, int size, String sortBy, String direction) {
        ResponseEntity<UserDTO> currentUser = authClient.getCurrentUser();
        ClinicOwner clinicOwner = clinicOwnerRepository.findByUserId(currentUser.getBody().getUserId());

        // Create Sort object based on direction
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        // Create Pageable object
        Pageable pageable = PageRequest.of(page, size, sort);

        // Get page of clinics from repository
        Page<Clinic> clinicPage = clinicRepository.findByOwner_OwnerId(clinicOwner.getOwnerId(), pageable);

        // Map entities to DTOs
        Page<ClinicDTO> clinicDTOPage = clinicPage.map(clinic -> {
            // Use your existing mapper or create a DTO manually
            return ClinicMapper.toDto(clinic);
        });

        return new ResponseData<>(200, "Clinics retrieved successfully", clinicDTOPage);
    }

    @Override
    public ResponseData<ClinicOwnerDTO> getClinicOwnerByClinicId(UUID clinicId) {
        Clinic clinic = clinicRepository.findById(clinicId).orElseThrow(() -> new RuntimeException("clinic not found"));
        ClinicOwner clinicOwner = clinicOwnerRepository.findById(clinic.getOwner().getOwnerId()).orElseThrow(() -> new RuntimeException("clinicOwner not found"));
        ClinicOwnerDTO data = ClinicMapper.toClinicOwnerDTO(clinicOwner);

        return new ResponseData<>(200, "ClinicOwner retrieved successfully", data);
    }

    @Override
    public ResponseData<Page<ClinicOwnerDTO>> getAllClinicOwner(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ClinicOwner> ownerPage = clinicOwnerRepository.findAll(pageable);

        Page<ClinicOwnerDTO> dto = ownerPage.map(ClinicMapper::toClinicOwnerDTO);

        return  new ResponseData<>(200, "Clinics retrieved successfully", dto);

    }


}
