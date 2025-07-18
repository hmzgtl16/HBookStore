package org.example.hbookstore.user.mapping;

import org.example.hbookstore.user.api.dto.CreateUserRequest;
import org.example.hbookstore.user.api.dto.UpdateUserRequest;
import org.example.hbookstore.user.api.dto.UserResponse;
import org.example.hbookstore.user.domain.User;
import org.example.hbookstore.user.domain.enums.UserStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class UserMapper {

    public User toEntity(CreateUserRequest request) {
        return new User(
                null, // ID will be set by the database
                request.username(),
                "", // Will be set by service
                request.role(),
                UserStatus.ACTIVE,
                Instant.now(),
                Instant.now()
        );
    }

    public User updateEntity(User user, UpdateUserRequest request) {
        if (request.role() != null) {
            user.setRole(request.role());
        }
        if (request.status() != null) {
            user.setStatus(request.status());
        }
        user.setUpdatedAt(Instant.now());
        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

}
