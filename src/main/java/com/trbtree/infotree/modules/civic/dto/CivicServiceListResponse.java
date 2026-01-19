package com.trbtree.infotree.modules.civic.dto;

import java.util.List;

public record CivicServiceListResponse(long totalElements,
                                       int totalPages,
                                       int pageNumber,
                                       int pageSize,
                                       boolean first,
                                       boolean last, List<CivicServiceResponseDto> content) {
}
