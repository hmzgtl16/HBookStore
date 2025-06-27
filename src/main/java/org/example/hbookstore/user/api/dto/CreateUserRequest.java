package org.example.hbookstore.user.api.dto;

import org.example.hbookstore.user.domain.enums.UserRole;

/**
 * DTO for {@link org.example.hbookstore.user.domain.User}
 */
public record CreateUserRequest(
        String username,
        String password,
        UserRole role
) {
}