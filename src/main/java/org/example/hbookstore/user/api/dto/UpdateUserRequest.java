package org.example.hbookstore.user.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.example.hbookstore.user.domain.enums.UserRole;
import org.example.hbookstore.user.domain.enums.UserStatus;

@Schema(
        title = "Update User Request",
        description = "Request object for updating user details"
)
public record UpdateUserRequest(
        @Schema(
                title = "Password",
                description = "New password for the user",
                example = "NewP@ssw0rd123",
                minLength = 8,
                maxLength = 128,
                nullable = true
        )
        @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
        @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one number")
        @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter")
        @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
        @Pattern(regexp = ".*[!@#$%^&*(),.?\":{}|<>].*", message = "Password must contain at least one special character")
        String password,

        @Schema(
                title = "Role",
                description = "New role for the user",
                allowableValues = {"USER", "ADMIN"},
                nullable = true
        )
        UserRole role,

        @Schema(
                title = "Status",
                description = "New status for the user",
                allowableValues = {"ACTIVE", "INACTIVE"},
                nullable = true
        )
        UserStatus status
) {
}