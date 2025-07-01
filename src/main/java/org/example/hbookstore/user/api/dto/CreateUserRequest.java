package org.example.hbookstore.user.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.example.hbookstore.user.domain.enums.UserRole;

@Schema(
        title = "Create User Request",
        description = "Request object for creating a new user"
)
public record CreateUserRequest(
        @Schema(
                title = "Username",
                description = "Unique username for the user",
                example = "john_doe",
                pattern = "^[a-z][a-z0-9_.-]{2,19}$",
                minLength = 3,
                maxLength = 20
        )
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        @Pattern(regexp = "^[a-z][a-z0-9_.-]{2,19}$", message = "Username must start with a lowercase letter and can only contain lowercase letters, numbers, underscores, and hyphens")
        String username,

        @Schema(
                title = "Password",
                description = "Password for the user",
                example = "P@ssw0rd123",
                minLength = 8,
                maxLength = 128
        )
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
        @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one number")
        @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter")
        @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
        @Pattern(regexp = ".*[!@#$%^&*(),.?\":{}|<>].*", message = "Password must contain at least one special character")
        String password,

        @Schema(
                title = "Role",
                description = "Role of the user",
                allowableValues = {"USER", "ADMIN"}
        )
        @NotNull(message = "Role is required")
        UserRole role
) {
}