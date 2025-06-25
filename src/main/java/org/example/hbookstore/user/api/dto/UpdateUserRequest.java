package org.example.hbookstore.user.api.dto;

import org.example.hbookstore.user.domain.enums.UserRole;
import org.example.hbookstore.user.domain.enums.UserStatus;

/**
 * DTO for {@link org.example.hbookstore.user.domain.User}
 */
public record UpdateUserRequest(
        String email,
        String password,
        UserRole role,
        UserStatus status
) {
}