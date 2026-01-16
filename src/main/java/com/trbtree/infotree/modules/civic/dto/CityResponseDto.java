package com.trbtree.infotree.modules.civic.dto;
import com.trbtree.infotree.modules.civic.entity.City;

import java.time.OffsetDateTime;

public record CityResponseDto(
        Integer id,
        String name,
        String country,
        String adminLevel,
        Integer population,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public static CityResponseDto fromEntity(City city) {
        return new CityResponseDto(
                city.getId(),
                city.getName(),
                city.getCountry(),
                city.getAdminLevel(),
                city.getPopulation(),

                city.getCreatedAt(),
                city.getUpdatedAt()
        );
    }
}