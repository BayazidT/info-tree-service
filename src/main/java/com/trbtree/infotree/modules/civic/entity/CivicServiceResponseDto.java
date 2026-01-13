package com.trbtree.infotree.modules.civic.entity;
import java.time.OffsetDateTime;
import java.util.Map;

public record CivicServiceResponseDto(
        Long id,
        String title,
        String description,
        String address,
        String cityName,
        double latitude,
        double longitude,
        String categoryName,
        String contactPhone,
        String contactEmail,
        boolean is24h7,
        OffsetDateTime lastVerified,
        Map<String, Object> extraAttributes  // parsed from JSONB
) {
    // Optional: constructor from entity
    public static CivicServiceResponseDto fromEntity(CivicServiceEntity entity) {
        var loc = entity.getLocation();
        return new CivicServiceResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getAddress(),
                entity.getCity().getName(),
                loc.getY(),  // latitude
                loc.getX(),  // longitude
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