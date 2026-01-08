package com.trbtree.infotree.modules.user.dto;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String name,
        String email
) {}
