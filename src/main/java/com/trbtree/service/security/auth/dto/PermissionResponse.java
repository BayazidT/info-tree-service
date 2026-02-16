// src/main/java/com/tr/rms/modules/auth/dto/PermissionResponse.java
package com.trbtree.service.security.auth.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record PermissionResponse(
        UUID id,
        String name,
        String description,
        String module,
        LocalDateTime createdAt
) {}