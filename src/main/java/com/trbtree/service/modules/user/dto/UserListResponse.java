package com.trbtree.service.modules.user.dto;

import java.util.List;
public record UserListResponse(
        List<UserResponse> content,
        long totalElements,
        int totalPages,
        int pageNumber,
        int pageSize,
        boolean first,
        boolean last
) {}

