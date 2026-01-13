package com.trbtree.infotree.modules.civic.dto;

import com.trbtree.infotree.modules.civic.entity.City;

public record CitySummaryDto(
        Integer id,
        String name,
        String country,
        Double latitude,
        Double longitude
) {
    public static CitySummaryDto fromEntity(City city) {
        var loc = city.getLocation();
        return new CitySummaryDto(
                city.getId(),
                city.getName(),
                city.getCountry(),
                loc != null ? loc.getY() : null,
                loc != null ? loc.getX() : null
        );
    }
}
