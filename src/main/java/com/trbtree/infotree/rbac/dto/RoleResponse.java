package com.trbtree.infotree.rbac.dto;

import java.util.UUID;

public record RoleResponse(
        UUID id,
        String name
) {
}
