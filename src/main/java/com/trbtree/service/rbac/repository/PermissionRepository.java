package com.trbtree.service.rbac.repository;

import com.trbtree.service.rbac.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Set;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    @Query("SELECT p FROM Permission p JOIN p.rolePermissions rp WHERE rp.roleId = :roleId")
    Set<Permission> findByRoleId(UUID roleId);
}