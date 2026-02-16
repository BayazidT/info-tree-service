package com.trbtree.service.rbac.entity;

import java.io.Serializable;
import java.util.UUID;

public record UserRoleId(UUID userId, UUID roleId) implements Serializable {}