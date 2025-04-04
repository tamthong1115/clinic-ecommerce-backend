    package com.fg.clinicservice.clinic.model;

    import org.springframework.stereotype.Component;

    @Component
    public class ClinicMapper {
        public static ClinicDto toDto (Clinic clinic) {
            if(clinic==null) return null;

            return ClinicDto.builder()
                    .clinicName(clinic.getClinicName())
                    .clinicAddress(clinic.getClinicAddress())
                    .clinicPhone(clinic.getClinicPhone())
                    .status(clinic.getStatus().name())
                    .build();
        }

        public static Clinic fromDto (ClinicDto clinicDto) {
            if(clinicDto==null) return null;

            Clinic clinic = new Clinic();
            clinic.setClinicName(clinicDto.getClinicName());
            clinic.setClinicAddress(clinicDto.getClinicAddress());
            clinic.setClinicPhone(clinicDto.getClinicPhone());

            try {
                clinic.setStatus(Clinic.Status.valueOf(clinicDto.getStatus().toUpperCase()));
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
            clinic.setStatus(Clinic.Status.CLOSED);
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
