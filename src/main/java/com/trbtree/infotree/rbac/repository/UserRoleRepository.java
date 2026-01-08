package com.trbtree.infotree.rbac.repository;

import com.trbtree.infotree.rbac.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {
}
