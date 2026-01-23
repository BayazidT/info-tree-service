package com.trbtree.infotree.modules.civic.dto;

import java.util.List;

public record DoctorListResponseDto(
        long totalElements,
        int totalPages,
        int pageNumber,
        int pageSize,
        boolean first,
        boolean last,
        List<DoctorResponseDto> content
) {}
