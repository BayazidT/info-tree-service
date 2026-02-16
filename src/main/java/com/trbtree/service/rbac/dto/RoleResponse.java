package com.trbtree.service.rbac.dto;

import java.util.UUID;

public record RoleResponse(
        UUID id,
        String name
) {
}
