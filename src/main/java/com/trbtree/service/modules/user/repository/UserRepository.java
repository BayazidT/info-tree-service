package com.trbtree.service.modules.user.repository;
import com.trbtree.service.modules.user.entity.User;
import org.springframework.data.jpa.repository.Query;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.deleted = false AND u.active = true")
    Optional<User> findActiveUserById(UUID id);
}
