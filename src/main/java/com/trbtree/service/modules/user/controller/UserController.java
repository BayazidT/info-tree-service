package com.trbtree.service.modules.user.controller;

import com.trbtree.service.modules.user.dto.UserListResponse;
import com.trbtree.service.modules.user.dto.UserRequest;
import com.trbtree.service.modules.user.dto.UserResponse;
import com.trbtree.service.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/info-tree-service/api/v1/private/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse create(@RequestBody UserRequest request) {
        return userService.create(request);
    }

    @GetMapping
//    @PreAuthorize("hasAuthority('user:read')")
    public UserListResponse getAll(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return userService.getAll(page, size);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable UUID id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable UUID id, @RequestBody UserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
    }
}
