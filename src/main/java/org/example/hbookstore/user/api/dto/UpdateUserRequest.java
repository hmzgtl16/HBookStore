package org.example.hbookstore.user.api.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.example.hbookstore.user.domain.enums.UserRole;
import org.example.hbookstore.user.domain.enums.UserStatus;

/**
 * DTO for {@link org.example.hbookstore.user.domain.User}
 */
public record UpdateUserRequest(
        @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
        @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one number")
        @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter")
        @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
        @Pattern(regexp = ".*[!@#$%^&*(),.?\":{}|<>].*", message = "Password must contain at least one special character")
        String password,
        UserRole role,
        UserStatus status
) {
}