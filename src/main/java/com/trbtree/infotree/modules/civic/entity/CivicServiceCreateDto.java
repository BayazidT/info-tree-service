package com.trbtree.infotree.modules.civic.entity;

import java.util.Map;

public record CivicServiceCreateDto(
        String title,
        String description,
        String address,
        Long cityId,
        double latitude,
        double longitude,
        Long categoryId,
        String contactPhone,
        String contactEmail,
        boolean is24h7,
        Map<String, Object> extraAttributes
) {}
