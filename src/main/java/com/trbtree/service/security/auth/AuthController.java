package com.trbtree.service.security.auth;

import com.trbtree.service.security.auth.dto.LoginRequest;
import com.trbtree.service.security.auth.dto.LoginResponse;
import com.trbtree.service.security.auth.dto.RefreshRequest;
import com.trbtree.service.security.auth.dto.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/info-tree-service/api/v1/public/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> me(Authentication auth) {
        return ResponseEntity.ok(authenticationService.getProfile(auth));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(authenticationService.refresh(request));
    }
}
