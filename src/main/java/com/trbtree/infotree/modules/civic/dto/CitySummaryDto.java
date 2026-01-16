package com.trbtree.infotree.modules.civic.dto;

import com.trbtree.infotree.modules.civic.entity.City;

public record CitySummaryDto(
        Integer id,
        String name,
        String country
) {
    public static CitySummaryDto fromEntity(City city) {
        return new CitySummaryDto(
                city.getId(),
                city.getName(),
                city.getCountry()
        );
    }
}
