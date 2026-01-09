package com.trbtree.infotree.rbac.service;
import com.trbtree.infotree.rbac.dto.RoleResponse;
import com.trbtree.infotree.rbac.entity.Role;
import com.trbtree.infotree.rbac.repository.RoleRepository;
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
