package org.example.hbookstore.user.api.dto;

import org.example.hbookstore.user.domain.enums.UserRole;
import org.example.hbookstore.user.domain.enums.UserStatus;

/**
 * DTO for {@link org.example.hbookstore.user.domain.User}
 */
public record UserResponse(
        Long id,
        String email,
        String username,
        UserRole role,
        UserStatus status,
        java.time.Instant createdAt,
        java.time.Instant updatedAt
) {
}