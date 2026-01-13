package com.trbtree.infotree.modules.civic.entity;
public record CivicServiceSummaryDto(
        Long id,
        String title,
        String address,
        String cityName,
        double distanceMeters,   // calculated in query/service
        boolean is24h7,
        String contactPhone
) {}