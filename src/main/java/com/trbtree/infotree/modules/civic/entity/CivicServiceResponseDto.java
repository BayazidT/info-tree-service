package com.trbtree.infotree.modules.civic.entity;
import java.time.OffsetDateTime;
import java.util.Map;

public record CivicServiceResponseDto(
        Long id,
        String title,
        String description,
        String address,
        String cityName,
        String categoryName,
        String contactPhone,
        String contactEmail,
        boolean is24h7,
        OffsetDateTime lastVerified,
        Map<String, Object> extraAttributes  // parsed from JSONB
) {
    // Optional: constructor from entity
    public static CivicServiceResponseDto fromEntity(CivicServiceEntity entity) {
        return new CivicServiceResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getAddress(),
                entity.getCity().getName(),
                entity.getCategory().getName(),
                entity.getContactPhone(),
                entity.getContactEmail(),
                entity.is24h7(),
                entity.getLastVerified(),
                // Parse JSONB string → Map (use Jackson/ObjectMapper in service layer)
                Map.of()  // placeholder – implement parsing
        );
    }
}