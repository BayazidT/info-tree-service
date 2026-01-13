package com.trbtree.infotree.modules.civic.dto;
import com.trbtree.infotree.modules.civic.entity.City;

import java.time.OffsetDateTime;

public record CityResponseDto(
        Integer id,
        String name,
        String country,
        String adminLevel,
        Integer population,
        Double latitude,          // extracted from Point
        Double longitude,         // extracted from Point
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public static CityResponseDto fromEntity(City city) {
        var loc = city.getLocation();
        return new CityResponseDto(
                city.getId(),
                city.getName(),
                city.getCountry(),
                city.getAdminLevel(),
                city.getPopulation(),
                loc != null ? loc.getY() : null,   // latitude
                loc != null ? loc.getX() : null,   // longitude
                city.getCreatedAt(),
                city.getUpdatedAt()
        );
    }
}