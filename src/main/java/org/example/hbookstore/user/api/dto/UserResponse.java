package org.example.hbookstore.user.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.hbookstore.user.domain.enums.UserRole;
import org.example.hbookstore.user.domain.enums.UserStatus;

import java.time.Instant;

@Schema(
        title = "User Response",
        description = "Response object for user details"
)
public record UserResponse(
        @Schema(description = "Unique identifier of the user")
        Long id,

        @Schema(description = "Username of the user")
        String username,

        @Schema(description = "Role of the user")
        UserRole role,

        @Schema(description = "Status of the user")
        UserStatus status,

        @Schema(description = "Creation timestamp of the user")
        Instant createdAt,

        @Schema(description = "Last updated timestamp of the user")
        Instant updatedAt
) {
}