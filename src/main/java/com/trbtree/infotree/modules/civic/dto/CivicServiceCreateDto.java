package com.trbtree.infotree.modules.civic.dto;

import jakarta.validation.constraints.*;
import java.time.OffsetDateTime;
import java.util.Map;

public record CivicServiceCreateDto(

        // Required common fields (from BaseEntity)
        @NotBlank @Size(max = 200)
        String title,

        @Size(max = 2000)
        String description,

        @NotBlank
        String address,

        @NotNull
        Long cityId,

        @NotNull
        Long categoryId,

        // Optional common fields
        Boolean isActive,

        // Civic-specific fields
        @NotBlank @Size(max = 50)
        String contactPhone,

        @Email @Size(max = 100)
        String contactEmail,

        Boolean is24h7,

        @NotNull
        @PastOrPresent
        OffsetDateTime lastVerified,               // ← changed to OffsetDateTime

        // Flexible catch-all
        @NotNull
        Map<String, Object> extraAttributes
) {
    // Optional police-specific validation
    public void validateForPolice() {
        if (!extraAttributes.containsKey("stationNumber")) {
            throw new IllegalArgumentException("Police entries require 'stationNumber' in extraAttributes");
        }
        if (!extraAttributes.containsKey("emergencyPhone")) {
            throw new IllegalArgumentException("Police entries require 'emergencyPhone' in extraAttributes");
        }
    }
}