package org.example.hbookstore.shared.error;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Schema(
        title = "Error Response",
        description = "Error response object containing details about the error that occurred."
)
public record ErrorResponse(
        @Schema(
                title = "Timestamp",
                description = "The timestamp when the error occurred.",
                example = "2023-10-01T12:00:00Z"
        )
        Instant timestamp,

        @Schema(
                title = "Status Code",
                description = "HTTP status code representing the error.",
                example = "404"
        )
        int status,

        @Schema(
                title = "Error",
                description = "Short description of the error.",
                example = "Not Found"
        )
        String error,

        @Schema(
                title = "Message",
                description = "Detailed message describing the error.",
                example = "User not found with id: 12345"
        )
        String message,

        @Schema(
                title = "Path",
                description = "The path of the request that caused the error.",
                example = "/api/v1/users/12345"
        )
        String path
) {

    public ErrorResponse(
            HttpStatus status,
            String message,
            String path
    ) {
        this(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path
        );
    }
}
