package com.trbtree.infotree.modules.civic.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CityCreateDto(
        @NotBlank @Size(min = 1, max = 100) String name,
        @Size(max = 100) String country,
        @Size(max = 50) String adminLevel,
        Integer population,
        Double latitude,
        Double longitude
) {}

