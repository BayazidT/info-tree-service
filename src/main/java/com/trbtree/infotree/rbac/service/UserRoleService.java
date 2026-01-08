package com.trbtree.infotree.rbac.service;

import com.trbtree.infotree.modules.user.entity.User;
import com.trbtree.infotree.rbac.entity.UserRole;
import com.trbtree.infotree.rbac.entity.Role;
import com.trbtree.infotree.rbac.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public void setUserRole(UUID userId, UUID roleId) {
        User user = new User();
        user.setId(userId);
        Role role = new Role();
        role.setId(roleId);
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRoleRepository.save(userRole);
    }
}
