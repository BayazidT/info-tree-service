// src/main/java/com/tr/rms/modules/auth/dto/RoleResponse.java
package com.trbtree.infotree.security.auth.dto;

import com.trbtree.infotree.rbac.dto.PermissionResponse;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record RoleResponse(
        UUID id,
        String name,
        String description,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Set<PermissionResponse> permissions
) {}