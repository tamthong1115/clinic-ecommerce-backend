package com.fg.clinicservice.service_clinic.service;

import com.fg.clinicservice.service_clinic.model.EService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<EService, UUID> {
    Optional<EService> findById(UUID uuid);
    List<EService> findAll();

    @Modifying
    @Query("UPDATE EService s SET s.active = :active WHERE s.serviceId = :serviceId")
    void updateServiceActiveStatus(@Param("serviceId") UUID serviceId, @Param("active") boolean active);

    @Query("SELECT s FROM EService s WHERE s.speciality.specialityId = :specialityId")
    Page<EService> findAllBySpecialityId(@Param("specialityId") UUID specialityId, Pageable pageable);


    @Query("SELECT s FROM EService s " +
            "JOIN s.clinicServices cs " +
            "WHERE (:specialty IS NULL OR s.speciality.specialityId = :specialty) " +
            "AND (:clinicId IS NULL OR cs.clinic.clinicId = :clinicId) " +
            "AND (:minPrice IS NULL OR s.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR s.price <= :maxPrice) " +
            "AND (:isActive IS NULL OR s.active = :isActive) " +
            "AND (:searchTerm IS NULL OR LOWER(s.serviceName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(s.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<EService> findByFilters(
            @Param("specialty") UUID specialty,
            @Param("clinicId") UUID clinicId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("isActive") Boolean isActive,
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );
}
