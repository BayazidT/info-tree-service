package com.trbtree.infotree.modules.civic.dto;

import java.util.Map;
import java.util.Set;

public record DoctorCreateDto(
        String title,
        String firstName,
        String lastName,
        String address,
        Long cityId,
        Long categoryId,                // e.g. "Allgemeinmediziner"
        Set<String> specialties,
        Set<String> languages,
        boolean acceptsNewPatients,
        String appointmentUrl,
        Map<String, String> consultationHours,
        Map<String, Object> extraAttributes
) {}
