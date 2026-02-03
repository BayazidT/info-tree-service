package com.trbtree.infotree.modules.civic.dto;

import com.trbtree.infotree.modules.civic.entity.DoctorEntity;

import java.util.Map;
public record DoctorResponseDto(
        Long id,
        String title,
        String firstName,
        String lastName,
        String fullName,                // computed field
        String address,
        String cityName,
        String departmentNameBn,
        String departmentNameEn,
//        Set<String> specialties,
        boolean acceptsNewPatients,
        boolean telemedicineAvailable,
        String appointmentUrl,
        Map<String, String> consultationHours,
        Map<String, Object> extraAttributes
) {

    // Static factory method – call it as DoctorResponseDto.fromEntity(entity)
    public static DoctorResponseDto fromEntity(DoctorEntity entity) {
        if (entity == null) {
            return null; // or throw exception
        }

        String fullName = String.format("%s %s %s",
                entity.getTitle() != null ? entity.getTitle() : "",
                entity.getFirstName(),
                entity.getLastName()
        ).trim();

        return new DoctorResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getFirstName(),
                entity.getLastName(),
                fullName,
                entity.getAddress(),
                entity.getCity() != null ? entity.getCity().getName() : null,
                entity.getDoctorDepartment() != null ? entity.getDoctorDepartment().getNameBn(): null,
                entity.getDoctorDepartment() != null ? entity.getDoctorDepartment().getNameEn(): null,
                entity.isAcceptsNewPatients(),
                entity.isTelemedicineAvailable(),
                entity.getAppointmentUrl(),
                Map.copyOf(entity.getConsultationHours()),     // immutable copy
                Map.copyOf(entity.getExtraAttributes())
        );
    }
}