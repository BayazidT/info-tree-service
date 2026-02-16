package com.trbtree.service.rbac.service;
import com.trbtree.service.rbac.dto.RoleResponse;
import com.trbtree.service.rbac.entity.Role;
import com.trbtree.service.rbac.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<RoleResponse> getRoles() {
        List<Role> roles = roleRepository.findAll();

        List<RoleResponse> roleResponses = new ArrayList<>();
        for (Role role : roles) {
            RoleResponse roleResponse = new RoleResponse(role.getId(), role.getName());
            roleResponses.add(roleResponse);
        }
        return roleResponses;
    }
}
