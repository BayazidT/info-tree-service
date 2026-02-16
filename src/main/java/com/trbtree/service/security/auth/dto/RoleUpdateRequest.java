// src/main/java/com/tr/rms/modules/auth/dto/RoleUpdateRequest.java
package com.trbtree.service.modules.auth.dto;

import jakarta.validation.constraints.Size;

public record RoleUpdateRequest(
        @Size(max = 50) String name,
        @Size(max = 255) String description,
        Boolean active
) {}