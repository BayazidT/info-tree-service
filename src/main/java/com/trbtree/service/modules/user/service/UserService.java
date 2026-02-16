package com.trbtree.service.modules.user.service;

import com.trbtree.service.exception.DataNotFoundException;
import com.trbtree.service.modules.user.dto.UserListResponse;
import com.trbtree.service.modules.user.dto.UserRequest;
import com.trbtree.service.modules.user.dto.UserResponse;
import com.trbtree.service.modules.user.entity.User;
import com.trbtree.service.modules.user.mapper.UserMapper;
import com.trbtree.service.modules.user.repository.UserRepository;
import com.trbtree.service.rbac.service.UserRoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final UserMapper mapper;

    @Transactional
    public UserResponse create(UserRequest request) {
        User saved = userRepository.save(mapper.toEntity(request));
        userRoleService.setUserRole(saved.getId(), request.roleId());
        return mapper.toResponse(saved);
    }


    public UserListResponse getAll(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> userResponses=  userRepository.findAll(pageable);
        return new UserListResponse(
                userResponses.stream()
                        .map(mapper::toResponse)
                        .toList(),
                userResponses.getTotalElements(),
                userResponses.getTotalPages(),
                userResponses.getNumber(),
                userResponses.getSize(),
                userResponses.isFirst(),
                userResponses.isLast()
        );
    }

    public UserResponse getById(UUID id) {
        User user = userRepository.findActiveUserById(id)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return mapper.toResponse(user);
    }

    public UserResponse update(UUID id, UserRequest request) {
        User user = userRepository.findActiveUserById(id)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        mapper.updateEntity(user, request);

        return mapper.toResponse(userRepository.save(user));
    }

    public void delete(UUID id) {
        User user = userRepository.findActiveUserById(id).orElseThrow(() -> new DataNotFoundException("User not found"));
        user.setDeleted(true);
        user.setActive(false);
        userRepository.save(user);
    }

}