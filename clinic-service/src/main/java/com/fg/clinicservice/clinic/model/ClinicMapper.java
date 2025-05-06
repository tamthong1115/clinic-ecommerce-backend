    package com.fg.clinicservice.clinic.model;

    import com.fg.clinicservice.clinic.dto.ClinicDTO;
    import org.springframework.stereotype.Component;

    @Component
    public class ClinicMapper {
        public static ClinicDTO toDto(Clinic clinic) {
            if (clinic == null) return null;

            return ClinicDTO.builder()
                    .clinicId(clinic.getClinicId())
                    .ownerId(clinic.getOwner() != null ? clinic.getOwner().getOwnerId() : null)
                    .ownerName(clinic.getOwner() != null ? clinic.getOwner().getLastName() : null)
                    .clinicName(clinic.getClinicName())
                    .email(clinic.getOwner() != null ? clinic.getOwner().getEmail() : null)
                    .clinicPhone(clinic.getClinicPhone())
                    .clinicAddress(clinic.getClinicAddress())
                    .description(clinic.getDescription())
                    .images(clinic.getImages())
                    .status(clinic.getStatus())
                    .build();
        }

        public static Clinic fromDto (ClinicDTO clinicDto) {
            if(clinicDto==null) return null;

            Clinic clinic = new Clinic();
            clinic.setClinicName(clinicDto.getClinicName());
            clinic.setClinicAddress(clinicDto.getClinicAddress());
            clinic.setClinicPhone(clinicDto.getClinicPhone());

            try {
                clinic.setStatus(Clinic.Status.valueOf(String.valueOf(clinicDto.getStatus())));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status: " + clinicDto.getStatus());
            }

            return clinic;
        }

        public static void updateClinicForm(Clinic clinic, ClinicForm clinicForm) {
            if(clinicForm == null || clinic==null) return;

            clinic.setClinicName(clinicForm.getClinicName());
            clinic.setClinicAddress(clinicForm.getClinicAddress());
            clinic.setClinicPhone(clinicForm.getClinicPhone());
            clinic.setStatus(clinicForm.getStatus());
        }

        public static Clinic fromForm(ClinicForm form) {
            if (form == null) return null;

            Clinic clinic = new Clinic();
            clinic.setClinicName(form.getClinicName());
            clinic.setClinicAddress(form.getClinicAddress());
            clinic.setClinicPhone(form.getClinicPhone());
            clinic.setStatus(Clinic.Status.CLOSED);
            return clinic;
        }
    }
