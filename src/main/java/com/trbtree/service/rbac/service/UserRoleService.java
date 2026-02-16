package com.trbtree.service.rbac.service;

import com.trbtree.service.modules.user.entity.User;
import com.trbtree.service.rbac.entity.UserRole;
import com.trbtree.service.rbac.entity.Role;
import com.trbtree.service.rbac.repository.UserRoleRepository;
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
